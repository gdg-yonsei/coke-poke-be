package gdsc.cokepoke.util;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
@NoArgsConstructor
public class SecurityUtil {

    // SecurityContext 에 유저 정보가 저장되는 시점
    // -> JwtFilter 의 doFilter 메소드에서 Request 가 들어올 때
    // getCurrentMemberId는 SecurityContext 에 저장된 member_id를 리턴하는 메소드
   public static Long getCurrentMemberId() {
       final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

       if (authentication == null || authentication.getName() == null) {
           throw new RuntimeException("Security Context에 인증 정보가 없습니다.");
       }
      return Long.parseLong(authentication.getName());
   }
}
