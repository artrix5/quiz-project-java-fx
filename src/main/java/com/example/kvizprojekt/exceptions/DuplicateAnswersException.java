package com.example.kvizprojekt.exceptions;

public class DuplicateAnswersException extends Exception {

    public DuplicateAnswersException() {
    }

    public DuplicateAnswersException(String message) {
        super(message);
    }

    public DuplicateAnswersException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateAnswersException(Throwable cause) {
        super(cause);
    }

    public DuplicateAnswersException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
