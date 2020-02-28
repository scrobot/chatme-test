package me.chat.test.api.services.task;

import me.chat.protoapi.ValidationStatus;
import me.chat.test.api.controllers.models.CreateTaskDto;
import me.chat.test.api.data.models.Task;
import reactor.core.publisher.Mono;

import java.util.List;

public interface TaskService {

    Mono<List<Task>> loadTasks();

    Mono<Task> createNewTask(CreateTaskDto dto);

    void markTaskAsVerified(long taskId, ValidationStatus status);

}
