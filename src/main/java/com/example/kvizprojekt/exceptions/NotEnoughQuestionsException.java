package com.example.kvizprojekt.exceptions;

public class NotEnoughQuestionsException extends Exception {

    public NotEnoughQuestionsException() {
    }

    public NotEnoughQuestionsException(String message) {
        super(message);
    }

    public NotEnoughQuestionsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughQuestionsException(Throwable cause) {
        super(cause);
    }

    public NotEnoughQuestionsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}