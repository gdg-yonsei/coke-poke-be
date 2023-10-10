package com.gdscys.cokepoke.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class ErrorResponse {
    private final Exception exception;
    private final String message;
    private final HttpStatus status;
    private List<ValidationError> errors;

    private ErrorResponse(Exception exception, String message, HttpStatus status) {
        this.exception = exception;
        this.message = message;
        this.status = status;
    }

    public static ErrorResponse of(Exception exception, String message, HttpStatus status) {
        return new ErrorResponse(exception, message, status);
    }

    @Getter
    @RequiredArgsConstructor
    private static class ValidationError {
        private final String field;
        private final String message;
    }

    public void addValidationError(String field, String message){
        if(Objects.isNull(errors)){
            errors = new ArrayList<>();
        }
        errors.add(new ValidationError(field, message));
    }
}
