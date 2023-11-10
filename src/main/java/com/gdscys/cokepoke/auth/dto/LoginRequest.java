package com.gdscys.cokepoke.auth.dto;

import com.gdscys.cokepoke.validation.declaration.ValidEmail;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class LoginRequest {

    @ValidEmail
    private String email;

    @NotBlank
    private String password;

    protected LoginRequest() {}

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}