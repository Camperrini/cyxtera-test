package com.cyxtera.technicaltest.controller;

import com.cyxtera.technicaltest.dto.OperationDto;
import com.cyxtera.technicaltest.dto.OperatorDto;
import com.cyxtera.technicaltest.dto.ResultDto;
import com.cyxtera.technicaltest.dto.SessionDto;
import com.cyxtera.technicaltest.exception.InvalidOperationException;
import com.cyxtera.technicaltest.exception.NotStoredNumbersException;
import com.cyxtera.technicaltest.exception.SessionNotFoundException;
import com.cyxtera.technicaltest.service.OperationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("operations")
public class OperationsController {

    @Autowired
    private OperationsService operationsService;

    @GetMapping("/")
    public ResponseEntity<?> createSession() {
        String sessionUuid = operationsService.createSession();
        return ResponseEntity.ok(new SessionDto(sessionUuid));
    }

    @PostMapping("/")
    public ResponseEntity<?> addOperator(@RequestBody OperatorDto operatorDto) throws SessionNotFoundException {
        operationsService.addOperandSession(operatorDto.getId(), operatorDto.getNumber());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/execute")
    public ResponseEntity<?> executeOperation(@RequestBody OperationDto operationDto) throws SessionNotFoundException, InvalidOperationException, NotStoredNumbersException {
        Double result = operationsService.executeOperation(operationDto.getId(), operationDto.getOperation());
        return ResponseEntity.ok(new ResultDto(result));
    }
}
