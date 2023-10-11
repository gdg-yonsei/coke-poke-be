package com.gdscys.cokepoke.member.dto;

import com.gdscys.cokepoke.validation.declaration.PasswordMatches;
import com.gdscys.cokepoke.validation.declaration.ValidEmail;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
@PasswordMatches
public class SignupRequest {

    @NotBlank
    @ValidEmail
    private String email;

    @NotBlank
    private String username;

    @NotBlank
    @Length(min = 8, max = 20)
    private String password;

    @NotBlank
    @Length(min = 8, max = 20)
    private String confirmPassword;

    protected SignupRequest() {}

    @Builder
    public SignupRequest(String email, String username, String password, String confirmPassword) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }
}
