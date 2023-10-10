package com.gdscys.cokepoke.friendship.service;

import com.gdscys.cokepoke.friendship.domain.Friendship;
import com.gdscys.cokepoke.friendship.repository.FriendshipRepository;
import com.gdscys.cokepoke.member.domain.Member;
import com.gdscys.cokepoke.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendshipService implements IFriendshipService {
    private final FriendshipRepository friendshipRepository;
    private final MemberService memberService;

    private static final int PAGE_SIZE = 15;

    @Override
    public void createFriendship(Member member, String recipientUsername) {
        Member recipientMember = memberService.findMemberByUsername(recipientUsername);
        Friendship friendship = new Friendship(member, recipientMember);
        friendshipRepository.save(friendship);
    }

    @Override
    public Friendship findFriendshipByMembers(Member member, String username2) {
        Member member2 = memberService.findMemberByUsername(username2);

        Optional<Friendship> friendship = friendshipRepository.findByRequestMemberAndRecipientMember(member, member2);
        Optional<Friendship> friendship2 = friendshipRepository.findByRequestMemberAndRecipientMember(member2, member);

        if (friendship.isEmpty() && friendship2.isEmpty()) throw new NoSuchElementException("No friendship found between " + member2.getUsername() + " and " + username2);
        else return friendship.orElseGet(friendship2::get);
    }

    @Override
    public List<Friendship> findFriendshipsByMember(Member member, int page) {
        PageRequest pageRequest = PageRequest.of(page, PAGE_SIZE);
        return friendshipRepository.findAllByRequestMemberOrRecipientMember(member, pageRequest)
                .stream()
                .collect(Collectors.toList());
    }

}
