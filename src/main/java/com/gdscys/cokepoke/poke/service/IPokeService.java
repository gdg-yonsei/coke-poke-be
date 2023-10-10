package com.gdscys.cokepoke.poke.service;

import com.gdscys.cokepoke.poke.domain.Poke;

import java.util.List;

public interface IPageService {
    List<Poke> getPokesByFriendshipId(Long friendshipId, int page);
    Poke getPokeById(Long pokeId);
    Poke poke(Long friendshipId, String username);

}
