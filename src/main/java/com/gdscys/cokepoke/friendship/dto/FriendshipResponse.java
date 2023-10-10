package com.gdscys.cokepoke.friendship.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FriendshipResponse {
    private String username1;
    private String username2;

    protected FriendshipResponse() {}

}
