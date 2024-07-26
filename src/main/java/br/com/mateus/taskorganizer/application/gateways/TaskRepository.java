package br.com.mateus.taskorganizer.application.gateways;

import java.util.List;

import br.com.mateus.taskorganizer.domain.task.Task;

public interface TaskRepository {

    public Task saveTask(Task task);
    public List<Task> getAllTasksByUserId(String id);
    public Task getTaskByIdAndUserId(String taskId, String userId);
    public void deleteTaskByIdAndUserId(String taskId, String userId);
}