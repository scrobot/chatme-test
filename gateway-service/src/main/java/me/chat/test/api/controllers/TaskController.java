package me.chat.test.api.controllers;


import lombok.AllArgsConstructor;
import me.chat.test.api.controllers.models.CreateTaskDto;
import me.chat.test.api.data.models.Task;
import me.chat.test.api.services.task.TaskService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping("all")
    public Mono<List<Task>> getTasks() {
        return taskService.loadTasks();
    }

    @PostMapping("create")
    public Mono<Task> createTask(@RequestBody CreateTaskDto dto) {
        return taskService.createNewTask(dto);
    }

}
