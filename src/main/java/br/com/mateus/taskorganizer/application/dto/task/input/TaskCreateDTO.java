package br.com.mateus.taskorganizer.application.dto.task.input;

import java.time.LocalDate;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;

public record TaskCreateDTO(
		
	@NotBlank
	String title,
		
	String description,
		
	LocalDate dueDate) {
	
	@AssertTrue
	private boolean validateDueDate() {
		if (this.dueDate == null) return true;
		if (dueDate.isBefore(LocalDate.now())) return true;
		return false;
	}
}