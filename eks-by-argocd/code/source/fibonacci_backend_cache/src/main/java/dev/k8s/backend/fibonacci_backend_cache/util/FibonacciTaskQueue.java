package dev.k8s.backend.fibonacci_backend_cache.util;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FibonacciTaskQueue {

    private final StringRedisTemplate stringRedisTemplate;

    public FibonacciTaskQueue(
        StringRedisTemplate stringRedisTemplate
    ){
        this.stringRedisTemplate = stringRedisTemplate;
    }

    private static final String HASH_KEY = "fibonacci:task-queue";

    public long offerTask(int number) {
        stringRedisTemplate.opsForSet().add(HASH_KEY, String.valueOf(number));

        return Optional
                .ofNullable(stringRedisTemplate.opsForSet().size(HASH_KEY))
                .orElse(0L);
    }

}
