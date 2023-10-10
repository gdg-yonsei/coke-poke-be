package com.gdscys.cokepoke.member.dto;

import com.gdscys.cokepoke.validation.declaration.PasswordMatches;
import lombok.Builder;
import lombok.Getter;

@Getter
@PasswordMatches
public class UpdateMemberRequest {
    private String originalPassword;
    private String newPassword;
    private String confirmNewPassword;

    protected UpdateMemberRequest() {}

    @Builder
    public UpdateMemberRequest(String originalPassword, String newPassword, String confirmNewPassword) {
        this.originalPassword = originalPassword;
        this.newPassword = newPassword;
        this.confirmNewPassword = confirmNewPassword;
    }
}
