package com.cyxtera.technicaltest.service.impl;

import com.cyxtera.technicaltest.exception.InvalidOperationException;
import com.cyxtera.technicaltest.exception.NotStoredNumbersException;
import com.cyxtera.technicaltest.exception.SessionNotFoundException;
import com.cyxtera.technicaltest.model.Session;
import com.cyxtera.technicaltest.repository.redis.SessionRepository;
import com.cyxtera.technicaltest.service.OperationsService;

import java.util.Collections;

public class OperationsServiceImpl implements OperationsService {

    private final SessionRepository sessionRepository;

    public OperationsServiceImpl(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public String createSession() {
        Session session = new Session();
        session.setNumbers(Collections.emptyList());

        Session savedSession = sessionRepository.save(session);
        return savedSession.getId();
    }

    @Override
    public void addOperandSession(String uuid, Integer number) throws SessionNotFoundException {
        Session session = sessionRepository.findById(uuid).orElseThrow(() ->
                new SessionNotFoundException("A session with " + uuid + " does not exist"));
        session.getNumbers().add(number);
        sessionRepository.save(session);
    }

    @Override
    public Double executeOperation(String uuid, String operation) throws SessionNotFoundException, InvalidOperationException, NotStoredNumbersException {
        Session session = sessionRepository.findById(uuid).orElseThrow(() ->
                new SessionNotFoundException("A session with " + uuid + " does not exist"));
        if (session.getNumbers().size() < 2) {
            throw new NotStoredNumbersException("The session does not the required min numbers to perform the operation");
        }
        double result = (double) 0;
        switch (operation) {
            case "suma":
                result = session.getNumbers().stream().reduce(0, Integer::sum).doubleValue();
                break;
            case "resta":
                result = session.getNumbers().stream().reduce(0, (a, b) -> a - b).doubleValue();
                break;
            case "multiplicación":
                result = session.getNumbers().stream().reduce(0, (a, b) -> a * b).doubleValue();
                break;
            case "división":
                try {
                    result = session.getNumbers().get(0);
                    for (Integer number : session.getNumbers().subList(1, session.getNumbers().size())) {
                        result /= number;
                    }
                } catch (ArithmeticException ex) {
                    throw new InvalidOperationException("Cannot perform the operation with the stored numbers");
                }
                break;
            case "potenciación":
                result = session.getNumbers().get(0);
                for (Integer number : session.getNumbers().subList(1, session.getNumbers().size())) {
                    result = Math.pow(result, number);
                }
                break;
        }
        return result;
    }
}
