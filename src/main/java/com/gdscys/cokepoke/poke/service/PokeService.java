package com.gdscys.cokepoke.poke.service;

import com.gdscys.cokepoke.friendship.domain.Friendship;
import com.gdscys.cokepoke.friendship.service.FriendshipService;
import com.gdscys.cokepoke.member.domain.Member;
import com.gdscys.cokepoke.poke.domain.Poke;
import com.gdscys.cokepoke.poke.repository.PokeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PokeService implements IPokeService {

    private final PokeRepository pokeRepository;
    private final FriendshipService friendshipService;

    private final int PAGE_SIZE = 15;

    @Override
    public List<Poke> getPokesByUsernames(String senderUsername, String receiverUsername, int page) {
        PageRequest pageRequest = PageRequest.of(page, PAGE_SIZE);
        Friendship friendship = friendshipService.getFriendshipByMembers(senderUsername, receiverUsername);
        Page<Poke> pokes = pokeRepository.findAllByFriendship(friendship, pageRequest);
        if (pokes.isEmpty()) throw new IllegalArgumentException("No pokes found");
        return pokes.stream().collect(Collectors.toList());
    }

    @Override
    public Poke getPokeById(Long pokeId) {
        return pokeRepository.findById(pokeId).orElseThrow(() -> new IllegalArgumentException("No poke found"));
    }

    @Override
    public Poke poke(String senderUsername, String receiverUsername) {
        Friendship friendship = friendshipService.getFriendshipByMembers(senderUsername, receiverUsername);
        checkOncePerDay(friendship, senderUsername);
        Poke poke = new Poke(friendship);
        pokeRepository.save(poke);
        friendship.addPoke(poke);
        return poke;
    }

    private void checkOncePerDay(Friendship friendship, String username) {
        Member member = friendship.getFrom().getUsername().equals(username) ? friendship.getFrom() : friendship.getTo();

        LocalDateTime todayStart = LocalDateTime.now(member.getTimezone()).with(LocalTime.MIN);
        LocalDateTime todayEnd = LocalDateTime.now(member.getTimezone()).with(LocalTime.MAX);

        List<Poke> pokes = friendship.getPokes().stream()
                .filter(poke -> poke.getCreatedAt().isAfter(todayStart)
                        && poke.getCreatedAt().isBefore(todayEnd)
                        && poke.getSenderUsername().equals(username))
                .collect(Collectors.toList());

        if (!pokes.isEmpty()) throw new IllegalArgumentException("You can only poke once per day");
    }
}
