package me.chat.test.validation.services;

import me.chat.common.models.ValidationTaskRequest;
import me.chat.common.models.ValidationTaskResponse;
import reactor.core.publisher.Mono;

public interface ValidationService {
    Mono<ValidationTaskResponse> validate(ValidationTaskRequest request);
}
