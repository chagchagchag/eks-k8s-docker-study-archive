package io.study.k8s.k8shello;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

@Component
public class FileLogger {
    public void write(String message){
        try{
            Files.writeString(
                Paths.get("/app/volume/cache-log.log"),
                LocalDateTime.now().toString() + ">>> write " + message + System.lineSeparator(),
                CREATE, APPEND
            );
        } catch (IOException e){
            System.out.println("mount error");
            throw new RuntimeException();
        }
    }
}
