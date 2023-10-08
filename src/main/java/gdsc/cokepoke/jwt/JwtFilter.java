package gdsc.cokepoke.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    private final TokenProvider tokenProvider;

    // doFilterInternal
    // JWT 토큰의 인증정보를 현재 실행중인 쓰레드의 SecurityContext에 저장하는 역할을 수행
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Request 헤더에서 JWT Token을 받아옴
        String jwt = resolveToken(request);
        String requestURI = request.getRequestURI();

        // 유효한 토큰인지 확인
        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옴
            Authentication authentication = tokenProvider.getAuthentication(jwt);
            // SecurityContext에 Authentication 객체를 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            // 유효하지 않은 토큰이거나, 토큰이 없는 경우
            logger.debug("유효하지 않은 JWT 토큰입니다");
        }

        filterChain.doFilter(request, response);
    }

    // resolveToken
    // Request Header에서 토큰 정보를 꺼내오기 위한 메소드
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.split(" ")[1].trim();
        }
        return null;
    }
}
