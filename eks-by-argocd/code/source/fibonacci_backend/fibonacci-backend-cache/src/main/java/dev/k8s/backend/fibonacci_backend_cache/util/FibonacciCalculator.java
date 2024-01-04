package dev.k8s.backend.fibonacci_backend_cache.util;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class FibonacciCalculator {
    private final RestClient fibonacciClient = RestClient.create();
    public Optional<BigDecimal> requestGetFibonacci(int number) {
        String result = fibonacciClient.get()
                .uri("http://fibonacci-backend-web-service:8080/fibonacci?number="+number)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    throw new RuntimeException("invalid server response "+ response.getStatusText());
                })
                .body(String.class);

        return Optional.ofNullable(new BigDecimal(result));
    }
}
