package me.chat.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// TODO: Remove the crutch. Make a separate package without spring dependencies
// I did not bother with the configuration of the gradle to exclude all spring dependencies.
@SpringBootApplication
public class CommonApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonApplication.class, args);
    }

}