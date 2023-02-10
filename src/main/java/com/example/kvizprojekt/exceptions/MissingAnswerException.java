package com.example.kvizprojekt.exceptions;

public class MissingAnswerException extends Exception {

    public MissingAnswerException() {
    }

    public MissingAnswerException(String message) {
        super(message);
    }

    public MissingAnswerException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingAnswerException(Throwable cause) {
        super(cause);
    }

    public MissingAnswerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
