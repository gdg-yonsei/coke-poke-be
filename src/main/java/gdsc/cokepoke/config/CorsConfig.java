package gdsc.cokepoke.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    // CORS 설정
    // 프론트엔드, 백엔드를 구분지어 개발하거나, 서로 다른 서버 환경에서 자원을 공유할 때,
    // CORS를 허용하지 않으면, 다른 출처의 자원을 요청하는 것이 불가능하다.
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*"); // 모든 출처 허용
        config.addAllowedHeader("*"); // 모든 헤더 허용
        config.addAllowedMethod("*"); // 모든 HTTP 메소드 허용 (GET, POST, PUT...)

        source.registerCorsConfiguration("/api/**", config);
        return new CorsFilter(source);
    }
}
