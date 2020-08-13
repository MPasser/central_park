package com.beta.demo.exception;

public class ChatMessageIsEmptyException extends Exception {
    public ChatMessageIsEmptyException() {
    }

    public ChatMessageIsEmptyException(String message) {
        super(message);
    }

    public ChatMessageIsEmptyException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChatMessageIsEmptyException(Throwable cause) {
        super(cause);
    }
}
