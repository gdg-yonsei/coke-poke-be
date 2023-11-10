package com.gdscys.cokepoke.member.service;

import com.gdscys.cokepoke.member.domain.Member;
import com.gdscys.cokepoke.member.dto.UpdateMemberRequest;

import java.time.ZoneId;
import java.util.List;

public interface IMemberService {
    Member saveMember(String email, String username, String password, String timezone, String address);
    Member getMemberByUsername(String username);
    void updateMember(Member member, UpdateMemberRequest request);
    void deleteMember(Member member);
    List<Member> findAll(int page);
}
