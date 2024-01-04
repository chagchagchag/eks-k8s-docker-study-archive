package dev.k8s.backend.fibonacci_backend_web.healthcheck;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/probe")
public class HealthCheckController {

    private final Logger logger = LoggerFactory.getLogger(HealthCheckController.class);

    @GetMapping("/startup")
    public String startupCheck(){
        logger.info("[startup probe] >>> OK");
        return "START UP OK";
    }

    @GetMapping("/ready")
    public String readinessCheck(){
        logger.info("[readiness probe] >>> OK");
        return "READY OK";
    }

    @GetMapping("/live")
    public String livenessCheck(){
        logger.info("[liveness probe] >>> OK");
        return "OK";
    }

}
