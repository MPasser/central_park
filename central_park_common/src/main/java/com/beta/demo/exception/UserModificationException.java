package com.beta.demo.exception;

public class UserModificationException extends Exception {
    public UserModificationException() {
    }

    public UserModificationException(String message) {
        super(message);
    }

    public UserModificationException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserModificationException(Throwable cause) {
        super(cause);
    }
}
