package com.inshining.poke.domain.dto;

import com.inshining.poke.domain.entity.user.User;

public record SignUpResponse(
        String username,
        String name
) {
    public static SignUpResponse from(User user) {
        return new SignUpResponse(
                user.getUsername(),
                user.getName()
        );
    }
}
