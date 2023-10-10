package com.gdscys.cokepoke.member.dto;

import com.gdscys.cokepoke.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberResponse {
    private String email;
    private String username;

    protected MemberResponse() {}

    public MemberResponse(String email, String username) {
        this.email = email;
        this.username = username;
    }

    public static MemberResponse of(Member member) {
        return MemberResponse.builder()
                .email(member.getEmail())
                .username(member.getUsername())
                .build();
    }
}
