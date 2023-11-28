package com.inshining.poke.domain.dto.follow;

import com.inshining.poke.domain.entity.user.User;

import java.util.List;

public record MyFriendResponse(User my, List<FriendName> friendNames) {

    public static MyFriendResponse from(User my, List<FriendName> friendNames){
        return new MyFriendResponse(
                my,
                friendNames
        );
    }
}
