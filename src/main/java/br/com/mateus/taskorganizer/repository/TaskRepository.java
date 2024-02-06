package br.com.mateus.taskorganizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mateus.taskorganizer.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
