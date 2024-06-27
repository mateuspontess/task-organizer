package br.com.mateus.taskorganizer.application.usecases.task;

import br.com.mateus.taskorganizer.application.dto.task.TaskUpdateDTO;
import br.com.mateus.taskorganizer.application.gateways.TaskRepository;
import br.com.mateus.taskorganizer.domain.task.Task;

public class UpdateTask {

    private final TaskRepository repository;

    public UpdateTask(TaskRepository repository) {
        this.repository = repository;
    }

    public Task updateTaskData(Long taskId, Long userId, TaskUpdateDTO updateData) {
		Task task = repository.getTaskByIdAndUserId(taskId, userId);
		task.updateTask(updateData.title(), updateData.description(), updateData.dueDate(), updateData.status());
        return this.repository.saveTask(task);
    }
}