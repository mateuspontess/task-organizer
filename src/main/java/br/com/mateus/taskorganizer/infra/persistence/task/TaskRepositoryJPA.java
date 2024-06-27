package br.com.mateus.taskorganizer.infra.persistence.task;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface TaskRepositoryJPA extends JpaRepository<TaskEntity, Long>{

	List<TaskEntity> findAllByUserId(Long id);
	
	void deleteByUserIdAndId(Long userId, Long taskId);

	TaskEntity findByIdAndUserId(Long taskId, Long userId);
}