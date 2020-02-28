package me.chat.test.api.services.validation;

import me.chat.protoapi.ValidationTaskRequest;
import me.chat.protoapi.ValidationTaskResponse;
import me.chat.test.api.data.models.Task;
import me.chat.test.api.services.task.TaskService;
import me.chat.test.api.utils.RedisTopicHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class ValidationServiceImpl implements ValidationService {

    private final Logger logger = LoggerFactory.getLogger(ValidationServiceImpl.class);

    private final TaskService taskService;
    private final ReactiveRedisTemplate<String, Task> redis;
    private final RSocketRequester rSocketRequester;
    private final RedisTopicHelper redisHelper;

    @Value("${rsocket.routes.validation}")
    private String rSocketValidationRoute;

    public ValidationServiceImpl(TaskService taskService, ReactiveRedisTemplate<String, Task> redis, RSocketRequester rSocketRequester, RedisTopicHelper redisHelper) {
        this.taskService = taskService;
        this.redis = redis;
        this.rSocketRequester = rSocketRequester;
        this.redisHelper = redisHelper;
    }

    @Override
    public void startValidationProcess() {
        logger.info("validation listener started");

        Flux.interval(Duration.ofMillis(1000))
            .flatMap(i -> redis.keys(redisHelper.topicAllKeys()))
            .flatMap(redis.opsForValue()::get)
            .filter(it -> !it.isVerified())
            .flatMap(this::requestValidation)
            .log()
            .metrics()
            .subscribe(result -> {
                if ( result.getError() != null ) {
                    logger.error(result.getError().getReason());
                } else {
                    taskService.markTaskAsVerified(result.getId(), result.getStatus());
                    redis.opsForValue().delete(redisHelper.specifiedTopicWithId(result.getId()));
                }
            });
    }

    private Mono<ValidationTaskResponse> requestValidation(Task task) {
        return rSocketRequester
            .route(rSocketValidationRoute)
            .data(
                ValidationTaskRequest
                    .newBuilder()
                    .setId(task.getId())
                    .setName(task.getName())
                    .build()
            )
            .retrieveMono(ValidationTaskResponse.class);
    }

}
