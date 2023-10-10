package com.gdscys.cokepoke.poke.service;

import com.gdscys.cokepoke.friendship.domain.Friendship;
import com.gdscys.cokepoke.friendship.service.FriendshipService;
import com.gdscys.cokepoke.poke.domain.Poke;
import com.gdscys.cokepoke.poke.repository.PokeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PageService implements IPageService{

    private final PokeRepository pokeRepository;
    private final FriendshipService friendshipService;

    private final int PAGE_SIZE = 15;

    @Override
    public List<Poke> getPokesByFriendshipId(Long friendshipId, int page) {
        PageRequest pageRequest = PageRequest.of(page, PAGE_SIZE);
        Page<Poke> pokes = pokeRepository.findAllByFriendship_Id(friendshipId, pageRequest);
        if (pokes.isEmpty()) throw new IllegalArgumentException("No pokes found");
        return pokes.stream().collect(Collectors.toList());
    }

    @Override
    public Poke getPokeById(Long pokeId) {
        return pokeRepository.findById(pokeId).orElseThrow(() -> new IllegalArgumentException("No poke found"));
    }

    @Override
    public Poke poke(Long friendshipId, String username) {
        Friendship friendship = friendshipService.getFriendshipById(friendshipId);
        Poke poke = new Poke(friendship);
        pokeRepository.save(poke);
        friendship.addPoke(poke);
        return poke;
    }
}
