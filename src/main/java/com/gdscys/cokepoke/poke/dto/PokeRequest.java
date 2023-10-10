package com.gdscys.cokepoke.poke.dto;

import lombok.Getter;

@Getter
public class PokeRequest {

    private String receiverUsername;

    protected PokeRequest() {}

    public PokeRequest(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }
}
