package br.com.mateus.taskorganizer.infra.persistence.task;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface TaskRepositoryMongoDB extends MongoRepository<TaskEntity, String>{

	List<TaskEntity> findAllByUserId(String id);
	
	void deleteByUserIdAndId(String userId, String taskId);

	TaskEntity findByIdAndUserId(String taskId, String userId);
}