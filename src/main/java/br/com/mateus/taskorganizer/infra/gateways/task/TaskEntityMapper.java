package br.com.mateus.taskorganizer.infra.gateways.task;

import java.util.List;

import br.com.mateus.taskorganizer.domain.task.Task;
import br.com.mateus.taskorganizer.infra.persistence.task.TaskEntity;

public class TaskEntityMapper {

    public TaskEntity toEntity(Task task) {
        return new TaskEntity(
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.getDueDate(),
            task.getStatus(),
            task.getUserId()
        );
    }

    public Task toTask(TaskEntity entity) {
        return new Task(
            entity.getId(),
            entity.getTitle(),
            entity.getDescription(),
            entity.getDueDate(),
            entity.getStatus(),
            entity.getUserId()
        );
    }

    public List<Task> toListTask(List<TaskEntity> allByUserId) {
        return allByUserId.stream()
            .map(this::toTask)
            .toList();
    }
}