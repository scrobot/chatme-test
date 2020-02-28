package me.chat.test.api.services.task;

import lombok.AllArgsConstructor;
import me.chat.protoapi.ValidationStatus;
import me.chat.test.api.controllers.models.CreateTaskDto;
import me.chat.test.api.data.models.Task;
import me.chat.test.api.data.TaskRepository;
import me.chat.test.api.utils.RedisTopicHelper;
import org.joda.time.DateTime;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@AllArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository repository;
    private final ReactiveRedisTemplate<String, Task> redis;
    private final RedisTopicHelper redisTopicHelper;

    @Override
    public Mono<List<Task>> loadTasks() {
        return Flux.fromIterable(repository.findAll())
            .collectList()
            .metrics()
            .log();
    }

    @Override
    public Mono<Task> createNewTask(CreateTaskDto dto) {
        return Flux.just(dto)
            .map(createTaskDto -> new Task().setName(dto.getTaskName()))
            .map(repository::save)
            .concatMap(task -> redis.opsForValue().set(redisTopicHelper.specifiedTopicWithId(task.getId()), task).map(i -> task))
            .next()
            .metrics()
            .log();
    }

    @Override
    public void markTaskAsVerified(long taskId, ValidationStatus status) {
        repository.findById(taskId)
            .ifPresent(task -> {
                task.setVerified(true)
                    .setUpdatedAt(DateTime.now().toDate())
                    .setStatus(convertValidationStatus(status));

                repository.save(task);
            });
    }

    private String convertValidationStatus(ValidationStatus status) {
        switch (status) {
            case PASSED:
                return "passed";
            case DECLINED:
                return "declined";
        }

        return "unknown";
    }
}
