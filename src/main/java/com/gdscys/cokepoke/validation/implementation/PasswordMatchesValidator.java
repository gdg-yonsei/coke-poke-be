package com.gdscys.cokepoke.validation.implementation;

import com.gdscys.cokepoke.member.dto.SignupRequest;
import com.gdscys.cokepoke.member.dto.UpdateMemberRequest;
import com.gdscys.cokepoke.validation.declaration.PasswordMatches;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        if (obj.getClass().equals(SignupRequest.class)) {
            return validateSignupRequest(obj);
        }
        if (obj.getClass().equals(UpdateMemberRequest.class)) {
            return validateUpdateUserRequest(obj);
        }
        return false;
    }

    public boolean validateSignupRequest(Object obj) {
        SignupRequest request = (SignupRequest) obj;
        return request.getPassword().equals(request.getConfirmPassword());
    }
    public boolean validateUpdateUserRequest(Object obj) {
        UpdateMemberRequest request = (UpdateMemberRequest) obj;
        return ((!request.getOriginalPassword().equals(request.getNewPassword()))
                && request.getNewPassword().equals(request.getConfirmNewPassword()));
    }
}
