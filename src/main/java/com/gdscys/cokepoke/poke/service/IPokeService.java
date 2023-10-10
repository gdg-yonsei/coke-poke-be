package com.gdscys.cokepoke.poke.service;

import com.gdscys.cokepoke.poke.domain.Poke;

import java.util.List;

public interface IPokeService {
    List<Poke> getPokesByUsernames(String senderUsername, String receiverName, int page);
    Poke getPokeById(Long pokeId);
    Poke poke(String senderUsername, String receiverUsername);

}
