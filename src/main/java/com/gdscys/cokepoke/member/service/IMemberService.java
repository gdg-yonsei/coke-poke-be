package com.gdscys.cokepoke.member.service;

import com.gdscys.cokepoke.member.domain.Member;
import com.gdscys.cokepoke.member.dto.UpdateMemberRequest;

import java.util.List;

public interface IMemberService {
    Member saveMember(String email, String username, String password);
    Member findMemberByUsername(String username);
    void updateMember(Member member, UpdateMemberRequest request);
    void deleteMember(Member member);
}
