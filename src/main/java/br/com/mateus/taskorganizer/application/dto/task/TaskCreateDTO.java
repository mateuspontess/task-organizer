package br.com.mateus.taskorganizer.application.dto.task;

import java.time.LocalDate;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskCreateDTO(
		
		@NotBlank
		String title,
		
		String description,
		
		@NotNull @Future
		LocalDate dueDate
) {}