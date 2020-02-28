package me.chat.test.api.services.task;

import me.chat.test.api.controllers.models.CreateTaskDto;
import me.chat.test.api.data.models.Task;
import me.chat.common.models.ValidationStatus;
import reactor.core.publisher.Mono;

import java.util.List;

public interface TaskService {

    Mono<List<Task>> loadTasks();

    Mono<Task> createNewTask(CreateTaskDto dto);

    void markTaskAsVerified(long taskId, ValidationStatus status);

}
