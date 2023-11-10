package com.gdscys.cokepoke.poke.dto;

import com.gdscys.cokepoke.poke.domain.Poke;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PokeResponse {
    private String senderUsername;
    private LocalDateTime createdAt;

    protected PokeResponse() {}

    public static PokeResponse of (Poke poke) {
        PokeResponse response = new PokeResponse();
        response.senderUsername = poke.getSenderUsername();
        response.createdAt = poke.getCreatedAt();
        return response;
    }
}
