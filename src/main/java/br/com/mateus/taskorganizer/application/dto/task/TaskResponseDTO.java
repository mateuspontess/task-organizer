package br.com.mateus.taskorganizer.application.dto.task;
import java.time.LocalDate;

import br.com.mateus.taskorganizer.domain.task.StatusTask;
import br.com.mateus.taskorganizer.domain.task.Task;

public record TaskResponseDTO(
		Long id,
		String title,
		String description,
		LocalDate dueDate,
		StatusTask status
		) {
	
	public TaskResponseDTO(Task t) {
		this(t.getId(), t.getTitle(), t.getDescription(), t.getDueDate(), t.getStatus());
	}
}