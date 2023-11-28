package com.inshining.poke.domain.dto.follow;

import com.inshining.poke.domain.entity.follow.Follow;

public record FollowResponse(Long followerId, Long followingId) {

    public static FollowResponse from(Follow follow){
        return new FollowResponse(
                follow.getFollowerUser().getId(),
                follow.getFollowingUser().getId()
        );
    }
}
