package me.chat.test.api.controllers;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api")
public class ApiController {

    public Mono<Void> getTasks() {
        return Mono.empty();
    }

    public Mono<Void> createTask() {
        return Mono.empty();
    }

}
