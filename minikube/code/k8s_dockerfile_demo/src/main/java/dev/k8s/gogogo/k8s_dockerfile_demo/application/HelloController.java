package dev.k8s.gogogo.k8s_dockerfile_demo.application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String getHello(){
        return "안녕하세요. 미니큐브 예제입니다.";
    }

}
