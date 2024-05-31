package com.Restaurant.application.exception.exceptions;

public class tableEmptyException extends RuntimeException {

    public tableEmptyException(String message) {
        super(message);
    }
}
