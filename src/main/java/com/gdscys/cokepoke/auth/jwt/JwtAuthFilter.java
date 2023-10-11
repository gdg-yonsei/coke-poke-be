package com.gdscys.cokepoke.auth.jwt;


import com.gdscys.cokepoke.auth.domain.JwtCode;
import com.gdscys.cokepoke.auth.domain.TokenInfo;
import com.gdscys.cokepoke.member.domain.RefreshToken;
import com.gdscys.cokepoke.member.repository.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> accessToken = extractTokenFromCookie(request, "accessToken");
        Optional<String> refreshToken = extractTokenFromCookie(request, "refreshToken");

        // 유효한 토큰인지 확인합니다.
        if (accessToken.isPresent() && jwtTokenProvider.validateToken(accessToken.get()) == JwtCode.ACCESS) {
            // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
            Authentication authentication = jwtTokenProvider.getAuthentication(accessToken.get());
            // SecurityContext 에 Authentication 객체를 저장합니다.
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else if (accessToken.isPresent() && jwtTokenProvider.validateToken(accessToken.get()) == JwtCode.EXPIRED) {
            log.info("Access token expired");

            // refresh token 검증
            if (refreshToken.isPresent() && jwtTokenProvider.validateToken(refreshToken.get()) == JwtCode.ACCESS) {

                Optional<RefreshToken> savedToken = refreshTokenRepository.findByRefreshToken(refreshToken.get());

                Claims claims = jwtTokenProvider.parseClaims(accessToken.get());

                if (savedToken.isPresent() && claims.get("email").equals(savedToken.get().getMember().getEmail())) {
                    // accessToken 으로 부터 Authentication 객체 추출
                    Authentication authentication = jwtTokenProvider.getAuthentication(accessToken.get());

                    // email 을 추출하여 accessToken, refreshToken 생성
                    TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication, savedToken.get().getMember().getEmail());

                    // 인증 객체 설정
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    // refreshToken 업데이트
                    savedToken.get().setRefreshToken(tokenInfo.getRefreshToken());
                    refreshTokenRepository.save(savedToken.get());

                    response.addCookie(jwtTokenProvider.generateCookie("refreshToken", tokenInfo.getRefreshToken()));
                    response.addCookie(jwtTokenProvider.generateCookie("accessToken", tokenInfo.getAccessToken()));

                    log.info("Reissue access token");
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private Optional<String> extractTokenFromCookie(HttpServletRequest request, String cookieName) {

        if (request.getCookies() == null || request.getCookies().length == 0) return Optional.empty();

        return Arrays.stream(request.getCookies())
                .sequential()
                .filter(cookie -> cookie.getName().equals(cookieName))
                .map(Cookie::getValue)
                .findFirst();
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // request 에서 요청 path 추출
        String path = request.getServletPath();

        // filter 에서 제외한 url 목록
        String[] excludedPaths = { "/auth/login", "/auth/signup", "/h2-console"};

        for (String excludedPath : excludedPaths) {
            if (path.startsWith(excludedPath)) {
                return true;
            }
        }

        return false;
    }
}