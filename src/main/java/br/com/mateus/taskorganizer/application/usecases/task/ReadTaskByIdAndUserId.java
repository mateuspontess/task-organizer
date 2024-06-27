package br.com.mateus.taskorganizer.application.usecases.task;

import br.com.mateus.taskorganizer.application.gateways.TaskRepository;
import br.com.mateus.taskorganizer.domain.task.Task;

public class ReadTaskByIdAndUserId {

    private final TaskRepository repository;

    public ReadTaskByIdAndUserId(TaskRepository repository) {
        this.repository = repository;
    }

    public Task getTaskByIdAndUserId(Long taskId, Long userId) {
        return this.repository.getTaskByIdAndUserId(taskId, userId);
    }
}