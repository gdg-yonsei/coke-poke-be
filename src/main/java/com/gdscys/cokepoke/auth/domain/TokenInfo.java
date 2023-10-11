package com.gdscys.cokepoke.auth.domain;

import lombok.Getter;

@Getter
public class TokenInfo {
    private String accessToken;
    private String refreshToken;

    protected TokenInfo() {}

    public TokenInfo(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
