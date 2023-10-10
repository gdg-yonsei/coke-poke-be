package com.gdscys.cokepoke.friendship.service;

import com.gdscys.cokepoke.friendship.domain.Friendship;
import com.gdscys.cokepoke.friendship.repository.FriendshipRepository;
import com.gdscys.cokepoke.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendshipService implements IFriendshipService {
    private final FriendshipRepository friendshipRepository;

    @Override
    public void createFriendship(Member requestMember, Member recipientMember) {
        Friendship friendship = new Friendship(requestMember, recipientMember);
        friendshipRepository.save(friendship);
    }

    @Override
    public void deleteFriendship(Member requestMember, Member recipientMember) {
        Friendship friendship = friendshipRepository.findByRequestMemberAndRecipientMember(requestMember, recipientMember)
                .orElseThrow(() -> new IllegalArgumentException("No friendship found between: " + requestMember.getUsername() + " and " + recipientMember.getUsername() + "."));
        friendshipRepository.delete(friendship);
    }
}
