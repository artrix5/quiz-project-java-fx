package com.example.kvizprojekt.exceptions;

public class EmptyFieldsException extends Exception {

    public EmptyFieldsException() {
    }

    public EmptyFieldsException(String message) {
        super(message);
    }

    public EmptyFieldsException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyFieldsException(Throwable cause) {
        super(cause);
    }

    public EmptyFieldsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
