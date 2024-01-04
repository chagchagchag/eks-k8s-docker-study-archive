package dev.k8s.backend.fibonacci_backend_cache.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class ApiKeyNotExistException extends RuntimeException{
    public ApiKeyNotExistException(String message) {
        super(message);
    }
}
