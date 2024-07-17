package com.compassuol.sp.challenge.msuser.domain.user.exception;

public class UserUniqueViolationException extends RuntimeException {
    public UserUniqueViolationException(String message) {
        super(message);
    }
}