package com.gdscys.cokepoke.member.controller;

import com.gdscys.cokepoke.member.domain.Member;
import com.gdscys.cokepoke.member.dto.MemberResponse;
import com.gdscys.cokepoke.member.service.MemberService;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/my")
    public ResponseEntity<MemberResponse> viewMy(@AuthenticationPrincipal Member member) {
        return ResponseEntity.ok(MemberResponse.of(member));
    }

    @GetMapping("/all")
    public ResponseEntity<List<MemberResponse>> viewAll(@AuthenticationPrincipal Member member,
                                                        @RequestParam @Min(0) int page) {
        List<MemberResponse> responses = memberService.findAll(page)
                .stream()
                .map(MemberResponse::of)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

}
