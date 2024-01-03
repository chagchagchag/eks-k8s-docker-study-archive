package dev.k8s.backend.fibonacci_backend_cache.application;

import dev.k8s.backend.fibonacci_backend_cache.util.FibonacciCalculator;
import dev.k8s.backend.fibonacci_backend_cache.util.FibonacciTaskQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FibonacciCacheController {
    @Value("${fibonacci.language}")
    private String language;

    @Value("${fibonacci.api-key}")
    private String apiKey;

    private final FibonacciCacheService fibonacciCacheService;
    private final FibonacciCalculator fibonacciCalculator;
    private final FibonacciTaskQueue fibonacciTaskQueue;

    public FibonacciCacheController(
        FibonacciCacheService fibonacciCacheService,
        FibonacciCalculator fibonacciCalculator,
        FibonacciTaskQueue fibonacciTaskQueue
    ){
        this.fibonacciCacheService = fibonacciCacheService;
        this.fibonacciCalculator = fibonacciCalculator;
        this.fibonacciTaskQueue = fibonacciTaskQueue;
    }

    @GetMapping("/fibonacci/{n}")
}
