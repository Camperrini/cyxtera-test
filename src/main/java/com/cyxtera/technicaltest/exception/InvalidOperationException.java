package com.cyxtera.technicaltest.exception;

public class InvalidOperationException extends CyxteraException {
    public InvalidOperationException(String message) {
        super(message, 400);
    }
}
