package dev.k8s.backend.fibonacci_backend_web.application.fibonacciservice;

import dev.k8s.backend.fibonacci_backend_web.application.FibonacciService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class GetFibonacciTest {

    private final FibonacciService fibonacciService = new FibonacciService();

    @Test
    public void 간단한_피보나치_테스트(){
        assertThat(fibonacciService.getFibonacci(0))
                .isEqualTo(BigDecimal.valueOf(0));

        assertThat(fibonacciService.getFibonacci(1))
                .isEqualTo(BigDecimal.valueOf(1));

        assertThat(fibonacciService.getFibonacci(2))
                .isEqualTo(BigDecimal.valueOf(1));

        assertThat(fibonacciService.getFibonacci(3))
                .isEqualTo(BigDecimal.valueOf(2));

        assertThat(fibonacciService.getFibonacci(50))
                .isEqualTo(BigDecimal.valueOf(12586269025L));

    }

}
