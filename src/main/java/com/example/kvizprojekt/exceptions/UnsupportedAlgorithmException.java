package com.example.kvizprojekt.exceptions;

public class UnsupportedAlgorithmException extends RuntimeException {

    public UnsupportedAlgorithmException() {
    }

    public UnsupportedAlgorithmException(String message) {
        super(message);
    }

    public UnsupportedAlgorithmException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedAlgorithmException(Throwable cause) {
        super(cause);
    }

    public UnsupportedAlgorithmException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
