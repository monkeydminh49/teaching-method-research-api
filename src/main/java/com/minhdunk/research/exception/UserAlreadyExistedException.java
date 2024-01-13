package com.minhdunk.research.exception;

public class UserAlreadyExistedException extends RuntimeException{
    public UserAlreadyExistedException(String message) {
        super(message);
    }
    public UserAlreadyExistedException(String message, Throwable cause) {
        super(message, cause);
    }
}
