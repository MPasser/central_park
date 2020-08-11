package com.beta.demo.exception;

public class UserModificationException extends Throwable {
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
