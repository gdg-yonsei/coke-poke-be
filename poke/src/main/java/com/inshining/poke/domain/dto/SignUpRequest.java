package com.inshining.poke.domain.dto;

public record SignUpRequest(
        String username,
        String password,
        String name
) {

}
