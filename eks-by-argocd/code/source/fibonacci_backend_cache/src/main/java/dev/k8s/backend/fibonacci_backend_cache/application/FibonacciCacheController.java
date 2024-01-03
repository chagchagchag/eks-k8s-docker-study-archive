package dev.k8s.backend.fibonacci_backend_cache.application;

import dev.k8s.backend.fibonacci_backend_cache.exception.ApiKeyNotExistException;
import dev.k8s.backend.fibonacci_backend_cache.util.FibonacciCalculator;
import dev.k8s.backend.fibonacci_backend_cache.util.FibonacciTaskQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class FibonacciCacheController {
    @Value("${fibonacci.language}")
    private String language;

    @Value("${fibonacci.api-key}")
    private String apiKey;

    private final FibonacciResultCacheService fibonacciResultCacheService;
    private final FibonacciCalculator fibonacciCalculator;
    private final FibonacciTaskQueue fibonacciTaskQueue;

    public FibonacciCacheController(
        FibonacciResultCacheService fibonacciResultCacheService,
        FibonacciCalculator fibonacciCalculator,
        FibonacciTaskQueue fibonacciTaskQueue
    ){
        this.fibonacciResultCacheService = fibonacciResultCacheService;
        this.fibonacciCalculator = fibonacciCalculator;
        this.fibonacciTaskQueue = fibonacciTaskQueue;
    }

    @GetMapping("/fibonacci/{number}")
    public String getFibonacci(
            @PathVariable("number") int number,
            @RequestParam(value = "api-key", required = false) String apiKey
    ){
        if(number > 10){
            if(!this.apiKey.equals(apiKey)){
                throw new ApiKeyNotExistException("10 이상의 수에 대한 피보나치 계산은 API KEY 가 필요합니다.");
            }
        }

        return fibonacciResultCacheService
                .selectFibonacci(number)
                .map(BigDecimal::toPlainString)
                .orElseGet(() -> {
                    if(number > 1000){
                        final long size = fibonacciTaskQueue.offerTask(number);
                        return switch (language){
                            case "ko" -> "fibonacci(" + number + ") 계산을 예약합니다. 남은 작업수 = " + size;
                            case "en" -> "fibonacci(" + number + ") has been scheduled. Remain task = " + size;
                            default -> "Unsupported Language";
                        };
                    }

                    return fibonacciCalculator
                            .requestGetFibonacci(number)
                            .map(result -> {
                                fibonacciResultCacheService.putResult(number, result);
                                return result.toPlainString();
                            })
                            .orElseThrow(() -> new IllegalStateException(
                                    "피보나치 연산에 문제가 발생했습니다."
                            ));

                });
    }
}
