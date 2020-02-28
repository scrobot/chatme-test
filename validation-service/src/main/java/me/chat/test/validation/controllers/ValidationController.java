package me.chat.test.validation.controllers;

import me.chat.common.models.ValidationTaskRequest;
import me.chat.common.models.ValidationTaskResponse;
import me.chat.test.validation.services.ValidationService;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class ValidationController {

    private final ValidationService service;

    public ValidationController(ValidationService service) {
        this.service = service;
    }

    @MessageMapping("currentMarketData")
    public Mono<ValidationTaskResponse> currentMarketData(ValidationTaskRequest request) {
        return service.validate(request);
    }

    @MessageExceptionHandler
    public Mono<ValidationTaskResponse> handleException(Exception e) {
        return Mono.just(ValidationTaskResponse.fromException(e));
    }
}
