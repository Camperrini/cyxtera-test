package com.cyxtera.technicaltest.exception;

import com.cyxtera.technicaltest.dto.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(CyxteraException.class)
    public ResponseEntity<?> cyxteraExceptionHandler(CyxteraException ex) {
        return ResponseEntity.status(ex.getErrorCode()).body(new ErrorDto(ex.getMessage(), ex.getErrorCode()));
    }
}
