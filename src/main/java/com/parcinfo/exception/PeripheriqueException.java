package com.parcinfo.exception;

public class PeripheriqueException extends RuntimeException {
    public PeripheriqueException(String message) {
        super(message);
    }

    public PeripheriqueException(String message, Throwable cause) {
        super(message, cause);
    }
} 