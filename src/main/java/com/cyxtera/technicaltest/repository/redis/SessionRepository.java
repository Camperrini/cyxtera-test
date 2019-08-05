package com.cyxtera.technicaltest.repository.redis;

import com.cyxtera.technicaltest.model.Session;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends CrudRepository<Session, String> {
}
