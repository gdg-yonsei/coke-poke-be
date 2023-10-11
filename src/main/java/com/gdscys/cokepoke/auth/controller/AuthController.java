package com.gdscys.cokepoke.auth.controller;

import com.gdscys.cokepoke.auth.domain.TokenInfo;
import com.gdscys.cokepoke.auth.dto.LoginRequest;
import com.gdscys.cokepoke.auth.service.CustomUserDetailsService;
import com.gdscys.cokepoke.member.domain.Member;
import com.gdscys.cokepoke.member.dto.SignupRequest;
import com.gdscys.cokepoke.member.dto.MemberResponse;
import javax.validation.Valid;

import com.gdscys.cokepoke.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.HttpHeaders.SET_COOKIE;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final MemberService memberService;
    private final CustomUserDetailsService userDetailsService;

    @PostMapping("/signup")
    public ResponseEntity<MemberResponse> signup(@RequestBody @Valid SignupRequest request) {
        Member member = memberService.saveMember(request.getEmail(), request.getUsername(), request.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MemberResponse.of(member));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<TokenInfo> login(@RequestBody @Valid LoginRequest loginRequest) {
        TokenInfo tokenInfo = userDetailsService.login(loginRequest.getEmail(), loginRequest.getPassword());
        return ResponseEntity.ok()
                .header(SET_COOKIE, generateCookie("accessToken", tokenInfo.getAccessToken()).toString())
                .header(SET_COOKIE, generateCookie("refreshToken", tokenInfo.getRefreshToken()).toString())
                .body(tokenInfo);
    }

    private ResponseCookie generateCookie(String from, String token) {
        return ResponseCookie.from(from, token)
                .httpOnly(true) // false로 하면 클라이언트도 쿠키로 접근할 수 있기 때문에 보안상 조치
                .path("/")
                .build();
    }
}
