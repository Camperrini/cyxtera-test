package com.cyxtera.technicaltest.service;

import com.cyxtera.technicaltest.exception.InvalidOperationException;
import com.cyxtera.technicaltest.exception.NotStoredNumbersException;
import com.cyxtera.technicaltest.exception.SessionNotFoundException;

public interface OperationsService {

    String createSession();

    void addOperandSession(String uuid, Integer number) throws SessionNotFoundException;

    Double executeOperation(String uuid, String operation) throws SessionNotFoundException, InvalidOperationException, NotStoredNumbersException;

}
