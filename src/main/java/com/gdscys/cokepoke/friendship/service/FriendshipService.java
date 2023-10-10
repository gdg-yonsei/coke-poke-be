package com.gdscys.cokepoke.friendship.service;

import com.gdscys.cokepoke.friendship.domain.Friendship;
import com.gdscys.cokepoke.friendship.repository.FriendshipRepository;
import com.gdscys.cokepoke.member.domain.Member;
import com.gdscys.cokepoke.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public void createFriendship(String username, String recipientUsername) {
        Member member = memberService.getMemberByUsername(username);
        Member to = memberService.getMemberByUsername(recipientUsername);
        if (member.equals(to)) throw new IllegalArgumentException("You cannot be friends with yourself");
        if (friendshipRepository.findByFromAndTo(member, to).isPresent()) {
            throw new IllegalArgumentException("You already sent a friend request");
        }

        Friendship friendship = new Friendship(member, to);
        friendshipRepository.save(friendship);
        member.addRequested(friendship);
        to.addReceived(friendship);
        checkIfFriendshipExists(friendship);
    }

    @Override
    public Friendship getFriendshipById(Long friendshipId) {
        return friendshipRepository.findById(friendshipId)
                .orElseThrow(() -> new NoSuchElementException("No friendship found with given id: "+ friendshipId));
    }

    @Override
    public Friendship getFriendshipByMembers(String username, String username2) {
        Member member = memberService.getMemberByUsername(username);
        Member to = memberService.getMemberByUsername(username2);

        Optional<Friendship> friendship = friendshipRepository.findByFromAndTo(member, to);
        Optional<Friendship> friendship2 = friendshipRepository.findByFromAndTo(to, member);

        if (friendship.isEmpty() && friendship2.isEmpty()) throw new NoSuchElementException("No friendship found between " + to.getUsername() + " and " + username2);
        else return friendship.orElseGet(friendship2::get);
    }

    @Override
    public List<Friendship> getFriendshipsByMember(String username, int page) {
        Member member = memberService.getMemberByUsername(username);
        PageRequest pageRequest = PageRequest.of(page, PAGE_SIZE);
        List<Friendship> friendships = friendshipRepository.findAllByFrom(member, pageRequest)
                .stream()
                .filter(Friendship::isAccepted)
                .collect(Collectors.toList());
        if (friendships.isEmpty()) throw new IllegalArgumentException("No friendships found");
        return friendships;
    }

    private void checkIfFriendshipExists(Friendship friendship) {
        Optional<Friendship> friendshipOptional = friendshipRepository.findByFromAndTo(friendship.getTo(), friendship.getFrom());
        if (friendshipOptional.isPresent()) {
            friendship.accept();
            friendshipOptional.get().accept();
        }
    }

}
