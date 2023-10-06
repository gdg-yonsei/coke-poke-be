package com.cokepoke.app.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateForm {
    @Size(min = 3, max = 25, message = "User Id must be over 3 and under 25 characters")
    @NotEmpty(message = "UserID is necessary")
    private String userId;

    @NotEmpty(message = "Password is necessary")
    private String password1;

    @NotEmpty(message = "Check password again")
    private String password2;

    @NotEmpty(message = "Email is necessary")
    @Email
    private String email;

    @NotEmpty(message = "User name is necessary")
    private String userName;



}
