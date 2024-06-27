package br.com.mateus.taskorganizer.infra.gateways.task;

import java.util.List;

import br.com.mateus.taskorganizer.application.gateways.TaskRepository;
import br.com.mateus.taskorganizer.domain.task.Task;
import br.com.mateus.taskorganizer.infra.persistence.task.TaskEntity;
import br.com.mateus.taskorganizer.infra.persistence.task.TaskRepositoryJPA;

public class TaskRepositoryImplJPA implements TaskRepository{

    private final TaskRepositoryJPA repositoryJPA;
    private final TaskEntityMapper mapper;

    public TaskRepositoryImplJPA(TaskRepositoryJPA repositoryJPA, TaskEntityMapper mapper) {
        this.repositoryJPA = repositoryJPA;
        this.mapper = mapper;
    } 

    
    @Override
    public Task saveTask(Task task) {
        TaskEntity entity = this.mapper.toEntity(task);
        this.repositoryJPA.save(entity);

        return this.mapper.toTask(entity);
    }

    @Override
    public List<Task> getAllTasksByUserId(Long id) {
        return this.mapper.toListTask(this.repositoryJPA.findAllByUserId(id));
    }

    @Override
    public Task getTaskByIdAndUserId(Long taskId, Long userId) {
        return this.mapper.toTask(this.repositoryJPA.findByIdAndUserId(taskId, userId));
    }

    @Override
    public void deleteTaskByIdAndUserId(Long taskId, Long userId) {
        this.repositoryJPA.deleteByUserIdAndId(userId, taskId);
    }
}