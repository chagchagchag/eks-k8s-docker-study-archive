package io.study.k8s.k8shello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
    private final FileLogger fileLogger;
    public HealthCheckController(FileLogger fileLogger){
        this.fileLogger = fileLogger;
    }

    @GetMapping("/healthcheck")
    public String healthcheck(){
        try{
            fileLogger.write("OK");
            return "OK";
        }
        catch(Exception e){
            return "Mount Error" + System.lineSeparator();
        }
    }
}
