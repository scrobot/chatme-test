package me.chat.test.validation.services;

import me.chat.common.models.ValidationStatus;
import me.chat.common.models.ValidationTaskRequest;
import me.chat.common.models.ValidationTaskResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ValidationServiceImpl implements ValidationService {

    // Live in-memory cache. It's just a concept, could be replaced to anything(LRU, redis, e.t.c)
    private Map<Long, ValidationTaskResponse> cache = new ConcurrentHashMap<>();

    @Override
    public Mono<ValidationTaskResponse> validate(ValidationTaskRequest request) {
        return Mono.just(request)
            .map(it -> cache.get(it.getId()))
            .switchIfEmpty(
                Mono.just(
                    new ValidationTaskResponse()
                    .setId(request.getId())
                    .setStatus(request.getName().length() > 5 ? ValidationStatus.PASSED : ValidationStatus.DECLINED)
                )
            )
            .doOnSuccess(validationTaskResponse -> {
                cache.put(validationTaskResponse.getId(), validationTaskResponse);
            });
    }
}
