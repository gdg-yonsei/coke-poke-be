package com.gdscys.cokepoke.friendship.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class FriendshipRequest {
    @NotBlank
    private String recipientUsername;

    protected FriendshipRequest() {}
}
