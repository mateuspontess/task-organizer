package br.com.mateus.taskorganizer.model.task;

import java.time.LocalDate;

public record TaskUpdateDTO(
		
		String title,
		String description,
		LocalDate dueDate,
		String status
) {}