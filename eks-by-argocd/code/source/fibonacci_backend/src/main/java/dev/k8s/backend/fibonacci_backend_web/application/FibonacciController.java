package dev.k8s.backend.fibonacci_backend_web.application;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class FibonacciController {

    private final FibonacciService fibonacciService;

    public FibonacciController(FibonacciService fibonacciService){
        this.fibonacciService = fibonacciService;
    }

    @GetMapping("/fibonacci")
    public ResponseEntity<BigDecimal> getFibonacci(
            @RequestParam("number") int number
    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(fibonacciService.getFibonacci(number));
    }

}
