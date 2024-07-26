package br.com.mateus.taskorganizer.application.usecases.task;

import br.com.mateus.taskorganizer.application.gateways.TaskRepository;

public class RemoveTaskByIdAndUserId {

    private final TaskRepository repository;

    public RemoveTaskByIdAndUserId(TaskRepository repository) {
        this.repository = repository;
    }

    public void deleteTaskByIdAndUserId(String taskId, String userId) {
        this.repository.deleteTaskByIdAndUserId(taskId, userId);
    }
}