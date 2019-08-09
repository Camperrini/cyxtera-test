package com.cyxtera.technicaltest.service;

import com.cyxtera.technicaltest.exception.SessionNotFoundException;
import com.cyxtera.technicaltest.model.Session;
import com.cyxtera.technicaltest.repository.redis.SessionRepository;
import com.cyxtera.technicaltest.service.impl.OperationsServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class OperationsServiceTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Mock
    private SessionRepository sessionRepository;

    private OperationsService operationsService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        operationsService = new OperationsServiceImpl(sessionRepository);
    }

    @Test
    public void testCreateSession() {
        Session session = new Session();
        session.setId("1");

        when(sessionRepository.save(any())).thenReturn(session);

        String uuid = operationsService.createSession();

        Assert.assertEquals("The id must match", "1", uuid);
    }

    @Test
    public void testAddOperandSessionNoObjectFound() throws SessionNotFoundException {
        exceptionRule.expect(SessionNotFoundException.class);
        exceptionRule.expectMessage("A session with 1 does not exist");

        when(sessionRepository.findById(any())).thenReturn(Optional.empty());
        operationsService.addOperandSession("1", 20);
    }
}
