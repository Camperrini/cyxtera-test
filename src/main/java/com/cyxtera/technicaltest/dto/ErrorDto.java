package com.cyxtera.technicaltest.dto;
import lombok.Data;

import java.util.Date;

@Data
public class ErrorDto {
    private Date timestamp;
    private String message;
    private Integer statusCode;

    public ErrorDto(String message, Integer statusCode){
        this.message = message;
        this.statusCode = statusCode;
        this.timestamp = new Date();
    }
}
