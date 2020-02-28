package me.chat.test.api.data;

import me.chat.test.api.data.models.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Long> {

}
