package dev.k8s.backend.fibonacci_backend_cache.healthcheck;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthcheckController {

    @GetMapping("/probe/healthcheck")
    public String healthcheck(){
        return "OK";
    }

}
