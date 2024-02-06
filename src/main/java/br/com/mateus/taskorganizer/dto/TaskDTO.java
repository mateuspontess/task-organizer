package br.com.mateus.taskorganizer.dto;

import java.time.LocalDate;

import br.com.mateus.taskorganizer.model.StatusTask;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskDTO(
		
		Long id,
		@NotBlank
		String title,
		@NotBlank
		String description,
		@NotNull @Future
		LocalDate dueDate,
		@NotNull
		StatusTask status
		) {
}
