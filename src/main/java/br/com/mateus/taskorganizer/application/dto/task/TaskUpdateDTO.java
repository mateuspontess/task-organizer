package br.com.mateus.taskorganizer.application.dto.task;
import java.time.LocalDate;

import br.com.mateus.taskorganizer.domain.task.StatusTask;

public record TaskUpdateDTO(
		
	String title,
	String description,
	LocalDate dueDate,
	StatusTask status
) {}