package com.inshining.poke.domain.dto.follow;

import com.inshining.poke.domain.entity.user.User;

public record FriendName(String friendName) {
    public static FriendName from(User user){
        String name = user.getName();
        return new FriendName(name);
    }
    public static FriendName of(String name){
        return new FriendName(name);
    }
}
