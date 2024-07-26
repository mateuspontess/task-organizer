package br.com.mateus.taskorganizer.infra.gateways.task;

import java.util.List;

import br.com.mateus.taskorganizer.application.gateways.TaskRepository;
import br.com.mateus.taskorganizer.domain.task.Task;
import br.com.mateus.taskorganizer.infra.persistence.task.TaskEntity;
import br.com.mateus.taskorganizer.infra.persistence.task.TaskRepositoryMongoDB;

public class TaskRepositoryImplMongoDB implements TaskRepository{

    private final TaskRepositoryMongoDB repositoryMongoDB;
    private final TaskEntityMapper mapper;

    public TaskRepositoryImplMongoDB(TaskRepositoryMongoDB mongoRepository, TaskEntityMapper mapper) {
        this.repositoryMongoDB = mongoRepository;
        this.mapper = mapper;
    } 

    
    @Override
    public Task saveTask(Task task) {
        TaskEntity entity = this.mapper.toEntity(task);
        this.repositoryMongoDB.save(entity);

        return this.mapper.toTask(entity);
    }

    @Override
    public List<Task> getAllTasksByUserId(String id) {
        return this.mapper.toListTask(this.repositoryMongoDB.findAllByUserId(id));
    }

    @Override
    public Task getTaskByIdAndUserId(String taskId, String userId) {
        System.out.println("TASKID: " + taskId + ", USERID: " + userId);
        return this.mapper.toTask(this.repositoryMongoDB.findByIdAndUserId(taskId, userId));
    }

    @Override
    public void deleteTaskByIdAndUserId(String taskId, String userId) {
        this.repositoryMongoDB.deleteByUserIdAndId(userId, taskId);
    }
}