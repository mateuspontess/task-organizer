package br.com.mateus.taskorganizer.application.usecases.task;

import br.com.mateus.taskorganizer.application.gateways.TaskRepository;
import br.com.mateus.taskorganizer.domain.task.Task;

public class SaveTask {

    private final TaskRepository repository;

    public SaveTask(TaskRepository repository) {
        this.repository = repository;
    }

    public Task registerTask(Task task) {
        return this.repository.saveTask(task);
    }
}