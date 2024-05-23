package br.com.mateus.taskorganizer.model.task;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

	List<Task> findAllByUserId(Long id);
	
	void deleteByUserIdAndId(Long userId, Long taskId);

	Task findByIdAndUserId(Long taskId, Long userId);
}