package com.gdscys.cokepoke.friendship.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FriendshipRequest {
    private String requestUsername;
    private String recipientUsername;

    protected FriendshipRequest() {}
}
