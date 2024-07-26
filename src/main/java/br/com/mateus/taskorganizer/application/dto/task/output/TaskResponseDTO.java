package br.com.mateus.taskorganizer.application.dto.task.output;
import java.time.LocalDate;

import br.com.mateus.taskorganizer.domain.task.StatusTask;
import br.com.mateus.taskorganizer.domain.task.Task;

public record TaskResponseDTO(
		String id,
		String title,
		String description,
		LocalDate dueDate,
		StatusTask status
	) {
	
	public TaskResponseDTO(Task t) {
		this(t.getId(), t.getTitle(), t.getDescription(), t.getDueDate(), t.getStatus());
	}
}