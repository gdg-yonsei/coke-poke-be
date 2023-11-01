package com.inshining.poke.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtDecoderFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @Value("${issuer}")
    private String issuer;

    @Value("${algorithmWay}")
    private String algorithmWay;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(AUTHORIZATION_HEADER);
        if (header != null && header.startsWith(BEARER_PREFIX)) {
            try {
                String accessToken = header.substring(BEARER_PREFIX.length());
                Authentication authenticationToken = getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } catch (JWTVerificationException exception){
                exception.printStackTrace();
            }
        }
        filterChain.doFilter(request, response);
    }

    private Authentication getAuthentication(String token){
        Algorithm algorithm = Algorithm.HMAC256(algorithmWay);
        JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String username = decodedJWT.getSubject();

        User user = new User(username, "", List.of(new SimpleGrantedAuthority("normal")));
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }
}
