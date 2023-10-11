package com.gdscys.cokepoke.member.service;

import com.gdscys.cokepoke.auth.jwt.JwtTokenProvider;
import com.gdscys.cokepoke.auth.domain.TokenInfo;
import com.gdscys.cokepoke.member.repository.RefreshTokenRepository;
import com.gdscys.cokepoke.member.domain.Member;

import com.gdscys.cokepoke.member.domain.RefreshToken;
import com.gdscys.cokepoke.member.dto.UpdateMemberRequest;
import com.gdscys.cokepoke.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MemberService implements IMemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    private final static int PAGE_SIZE = 15;

    @Override
    public Member saveMember(String email, String username, String password) {
        Member member = new Member(email, username, passwordEncoder.encode(password), Set.of("USER"));
        return memberRepository.save(member);
    }

    @Override
    public Member findMemberByUsername(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    @Override
    public void updateMember(Member member, UpdateMemberRequest request) {
        member.updatePassword(passwordEncoder.encode(request.getNewPassword()));
    }

    @Override
    public void deleteMember(Member member) {
        memberRepository.delete(member);
    }

}
