package br.com.mateus.taskorganizer.application.usecases.task;

import java.util.List;

import br.com.mateus.taskorganizer.application.gateways.TaskRepository;
import br.com.mateus.taskorganizer.domain.task.Task;

public class ReadAllTasksByUserId {

    private final TaskRepository repository;

    public ReadAllTasksByUserId(TaskRepository repository) {
        this.repository = repository;
    }

    public List<Task> getAllTasksByUserId(Long userId) {
        return this.repository.getAllTasksByUserId(userId);
    }
}