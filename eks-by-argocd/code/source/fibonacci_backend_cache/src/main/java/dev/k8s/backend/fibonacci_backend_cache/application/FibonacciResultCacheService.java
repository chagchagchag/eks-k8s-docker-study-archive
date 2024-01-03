package dev.k8s.backend.fibonacci_backend_cache.application;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class FibonacciResultCacheService {
    private final StringRedisTemplate stringRedisTemplate;

    public FibonacciResultCacheService(
            StringRedisTemplate stringRedisTemplate
    ){
        this.stringRedisTemplate = stringRedisTemplate;
    }

    private final String HASH_KEY = "fibonacci:result-set";

    public Optional<BigDecimal> selectFibonacci(int number){
        return getResult(String.valueOf(number))
                .map(str -> new BigDecimal(str));
    }

    public Optional<String> getResult(String key){
        HashOperations<String, String, String> hashOperations =
                stringRedisTemplate.opsForHash();

        return Optional
                .ofNullable(hashOperations.get(HASH_KEY, key));
    }

    public void putResult(int n, BigDecimal result){
        stringRedisTemplate.opsForHash().put(HASH_KEY, String.valueOf(n), result.toPlainString());
    }
}
