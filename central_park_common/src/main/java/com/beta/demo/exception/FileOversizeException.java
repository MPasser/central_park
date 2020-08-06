package com.beta.demo.exception;

public class FileOversizeException extends Exception {
    public FileOversizeException() {
    }

    public FileOversizeException(String message) {
        super(message);
    }

    public FileOversizeException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileOversizeException(Throwable cause) {
        super(cause);
    }
}
