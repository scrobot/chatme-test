package me.chat.test.api.services.task;

import me.chat.protoapi.ValidationStatus;
import me.chat.test.api.controllers.models.CreateTaskDto;
import me.chat.test.api.data.models.Task;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * A contract providing operations on the tasks
 */
public interface TaskService {

    /**
     * Loading task from persistence storage.
     *
     * @return {@code Publisher} that completes when data is returns to user, otherwise empty Mono-stream.
     */
    Mono<List<Task>> loadTasks();

    /**
     * Saving new task to persistence storage.
     *
     * @param dto Request payload.
     * @return {@code Publisher} that completes when new task is saved in data and sent in the validation service,
     * otherwise errors.
     */
    Mono<Task> createNewTask(CreateTaskDto dto);

    /**
     * Marking the task as verified and set specific status. Used as callback from validation service
     *
     * @param taskId Task identificator.
     * @param status the status of task validation(failed, success, e.t.c)
     */
    void markTaskAsVerified(long taskId, ValidationStatus status);

}
