package br.com.mateus.taskorganizer.model.task;

import java.time.LocalDate;

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
