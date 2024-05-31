package com.Restaurant.application.exception.exceptions;

public class emailTakenException extends RuntimeException {
    public emailTakenException(String message) {
        super(message);
    }
}
