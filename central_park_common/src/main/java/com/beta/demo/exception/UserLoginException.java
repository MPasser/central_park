package com.beta.demo.exception;

public class UserLoginException extends Exception {
    public UserLoginException() {
    }

    public UserLoginException(String message) {
        super(message);
    }

    public UserLoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserLoginException(Throwable cause) {
        super(cause);
    }
}
