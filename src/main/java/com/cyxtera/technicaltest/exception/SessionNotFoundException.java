package com.cyxtera.technicaltest.exception;

public class SessionNotFoundException extends CyxteraException {
    public SessionNotFoundException(String message) {
        super(message, 404);
    }
}
