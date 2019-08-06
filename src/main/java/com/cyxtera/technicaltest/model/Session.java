package com.cyxtera.technicaltest.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@Data
@RedisHash("session")
public class Session {
    @Id
    private String id;
    private List<Integer> numbers;
}
