package dev.k8s.backend.fibonacci_backend_web.application;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class FibonacciService {

    private final StringRedisTemplate stringRedisTemplate;

    public FibonacciService(StringRedisTemplate stringRedisTemplate){
        this.stringRedisTemplate = stringRedisTemplate;
    }

    private final Map<Integer, BigDecimal> dp = new HashMap<>();
    private static final String QUEUE_HASH_KEY = "fibonacci:task-queue";
    private static final String SET_HASH_KEY = "fibonacci:result-set";

    public BigDecimal getFibonacci(int number) {
        if(number == 0) return BigDecimal.ZERO;
        else if(number == 1) return BigDecimal.ONE;
        else if(number == 2) return BigDecimal.ONE;
        else{
            if(dp.containsKey(number)) return dp.get(number);

            dp.put(number, getFibonacci(number-2).add(getFibonacci(number-1)));

            return dp.get(number);
        }
    }

    // **TODO** : 'fibonacci-backend-batch' 로 아래 기능을 이관 예정.
    @Scheduled(fixedDelay = 1000L)
    public void scheduledCalculateFibonacci(){
        if(Boolean.TRUE.equals(stringRedisTemplate.hasKey(QUEUE_HASH_KEY))){
            Optional
                .ofNullable(stringRedisTemplate.opsForSet().pop(QUEUE_HASH_KEY))
                .ifPresent(cachedRequest -> {
                    BigDecimal result = getFibonacci(Integer.parseInt(cachedRequest));
                    stringRedisTemplate.opsForHash()
                            .put(
                                SET_HASH_KEY,
                                cachedRequest,
                                result.toPlainString()
                            );
                });
        }
    }

}
