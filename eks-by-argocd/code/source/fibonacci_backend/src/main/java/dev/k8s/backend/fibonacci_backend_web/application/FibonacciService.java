package dev.k8s.backend.fibonacci_backend_web.application;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class FibonacciService {

    private final Map<Integer, BigDecimal> dp = new HashMap<>();

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

}
