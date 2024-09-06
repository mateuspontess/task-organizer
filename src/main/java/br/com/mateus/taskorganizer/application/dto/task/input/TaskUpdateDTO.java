package br.com.mateus.taskorganizer.application.dto.task.input;
import java.time.LocalDate;

import br.com.mateus.taskorganizer.domain.task.StatusTask;

/* Fields are optional */
public record TaskUpdateDTO(
		
	String title,
	String description,
	LocalDate dueDate,
	StatusTask status) {}