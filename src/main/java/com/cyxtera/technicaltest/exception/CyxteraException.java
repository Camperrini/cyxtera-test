package com.cyxtera.technicaltest.exception;

import lombok.Data;

@Data
public class CyxteraException extends Exception {
    private String message;
    private Integer errorCode;

    public CyxteraException(String message, Integer errorCode) {
        super(message);
        this.message = message;
        this.errorCode = errorCode;
    }
}
