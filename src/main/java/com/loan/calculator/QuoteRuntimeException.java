package com.loan.calculator;

public class QuoteRuntimeException extends RuntimeException {

    public QuoteRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public QuoteRuntimeException(String message) {
        super(message);
    }
}
