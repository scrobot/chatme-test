package me.chat.test.validation.services;

import me.chat.protoapi.ValidationTaskRequest;
import me.chat.protoapi.ValidationTaskResponse;
import reactor.core.publisher.Mono;

public interface ValidationService {
    Mono<ValidationTaskResponse> validate(ValidationTaskRequest request);
}
