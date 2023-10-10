package com.gdscys.cokepoke.friendship.dto;

import com.gdscys.cokepoke.friendship.domain.Friendship;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FriendshipResponse {
    private String username1;
    private String username2;

    protected FriendshipResponse() {}

    public static FriendshipResponse of(Friendship friendship) {
        String username1 = friendship.getRequestMember().getUsername();
        String username2 = friendship.getRecipientMember().getUsername();
        return new FriendshipResponse(username1, username2);
    }

}
