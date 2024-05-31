package com.Restaurant.application.exception.exceptions;

public class entityNotExistException extends RuntimeException {
    public entityNotExistException(String message) {
        super(message);
    }
}
