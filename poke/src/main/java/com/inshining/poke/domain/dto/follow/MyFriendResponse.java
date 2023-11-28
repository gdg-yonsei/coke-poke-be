package com.inshining.poke.domain.dto.follow;

import com.inshining.poke.domain.entity.user.User;

import java.util.List;

public record MyFriendResponse(User my, List<User> friends) {

    public static MyFriendResponse from(User my, List<User> users){
        return new MyFriendResponse(
                my,
                users
        );
    }
}
