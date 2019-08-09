package com.cyxtera.technicaltest.exception;

public class NotStoredNumbersException extends CyxteraException {
    public NotStoredNumbersException(String message) {
        super(message, 400);
    }
}
