package com.cyxtera.technicaltest.service.impl;

import com.cyxtera.technicaltest.exception.InvalidOperationException;
import com.cyxtera.technicaltest.exception.NotStoredNumbersException;
import com.cyxtera.technicaltest.exception.SessionNotFoundException;
import com.cyxtera.technicaltest.model.Session;
import com.cyxtera.technicaltest.repository.redis.SessionRepository;
import com.cyxtera.technicaltest.service.OperationsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
@Slf4j
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
        log.info("New session created: {}", savedSession.getId());
        return savedSession.getId();
    }

    @Override
    public void addOperandSession(String uuid, Integer number) throws SessionNotFoundException {
        Session session = sessionRepository.findById(uuid).orElseThrow(() -> {
            log.error("Error: A session with id {} does not exist", uuid);
            return new SessionNotFoundException("A session with " + uuid + " does not exist");
        });
        if (session.getNumbers() == null) {
            session.setNumbers(new ArrayList<>());
        }
        session.getNumbers().add(number);
        sessionRepository.save(session);
        log.info("Added new operator {} to the session: {}", number, session.getId());
    }

    @Override
    public Double executeOperation(String uuid, String operation) throws SessionNotFoundException, InvalidOperationException, NotStoredNumbersException {
        Session session = sessionRepository.findById(uuid).orElseThrow(() -> {
            log.error("Error: A session with id {} does not exist", uuid);
            return new SessionNotFoundException("A session with " + uuid + " does not exist");
        });
        if (session.getNumbers().size() < 2) {
            log.error("Error: The session with id {} has less than 2 numbers", uuid);
            throw new NotStoredNumbersException("The session does not have the required min numbers to perform the operation");
        }
        Double result;
        switch (operation) {
            case "suma":
                result = session.getNumbers().get(0).doubleValue();
                for (Integer number : session.getNumbers().subList(1, session.getNumbers().size())) {
                    result += number;
                }
                break;
            case "resta":
                result = session.getNumbers().get(0).doubleValue();
                for (Integer number : session.getNumbers().subList(1, session.getNumbers().size())) {
                    result -= number;
                }
                break;
            case "multiplicación":
                result = session.getNumbers().get(0).doubleValue();
                for (Integer number : session.getNumbers().subList(1, session.getNumbers().size())) {
                    result *= number;
                }
                break;
            case "división":
                try {
                    result = session.getNumbers().get(0).doubleValue();
                    for (Integer number : session.getNumbers().subList(1, session.getNumbers().size())) {
                        result /= number;
                    }
                } catch (ArithmeticException ex) {
                    log.error(
                            "Error: Invalid number on the numbers array of the session {}, Numbers: {}, Operation: División",
                            uuid,
                            "[" + session.getNumbers().stream().map(Object::toString).collect(Collectors.joining(", ")) + "]"
                    );
                    throw new InvalidOperationException("Cannot perform the operation with the stored numbers");
                }
                break;
            case "potenciación":
                result = session.getNumbers().get(0).doubleValue();
                for (Integer number : session.getNumbers().subList(1, session.getNumbers().size())) {
                    result = Math.pow(result, number);
                }
                break;
            default:
                log.error("Error: The operation {} cannot be handled, session: {}", operation, session.getId());
                throw new InvalidOperationException("Invalid operation");
        }
        log.info(
                "The result of the operation is {} , Session: {}, Numbers: {}",
                operation,
                session.getId(),
                "[" + session.getNumbers().stream().map(Object::toString).collect(Collectors.joining(", ")) + "]"
        );
        session.setNumbers(new ArrayList<>());
        session.getNumbers().add(result.intValue());
        sessionRepository.save(session);
        return result;
    }
}
