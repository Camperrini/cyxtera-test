package com.cyxtera.technicaltest.model;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@Data
@RedisHash("session")
public class Session {
    private String uuid;
    private List<Integer> numbers;
}
