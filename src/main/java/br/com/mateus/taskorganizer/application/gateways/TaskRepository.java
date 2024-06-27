package br.com.mateus.taskorganizer.application.gateways;

import java.util.List;

import br.com.mateus.taskorganizer.domain.task.Task;

public interface TaskRepository {

    public Task saveTask(Task task);
    public List<Task> getAllTasksByUserId(Long id);
    public Task getTaskByIdAndUserId(Long taskId, Long userId);
    public void deleteTaskByIdAndUserId(Long taskId, Long userId);
}