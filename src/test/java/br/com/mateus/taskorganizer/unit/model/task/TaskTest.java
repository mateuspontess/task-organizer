package br.com.mateus.taskorganizer.unit.model.task;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.mateus.taskorganizer.model.task.StatusTask;
import br.com.mateus.taskorganizer.model.task.Task;

class TaskTest {
	
	private final String TITLE_VALIDATION_EXCEPTION_MESSAGE = "Title cannot be null or empty";
	private final String DUE_DATE_VALIDATION_EXCEPTION_MESSAGE = "Due date cannot be in the past";

	
	@Test
	@DisplayName("Should throw IllegalArgumentException if attribute 'title' is invalid")
	void instanciateTaskTest01() {
		// assert
		Assertions.assertThrows(
				IllegalArgumentException.class,
				() -> new Task(null, "description", LocalDate.now(), null),
				TITLE_VALIDATION_EXCEPTION_MESSAGE);
	}
	@Test
	@DisplayName("Should throw IllegalArgumentException if attribute 'dueDate' is invalid")
	void instanciateTaskTest02() {
		// assert
		Assertions.assertThrows(
				IllegalArgumentException.class,
				() -> new Task("Title", "description", LocalDate.now().minusDays(20L), null),
				DUE_DATE_VALIDATION_EXCEPTION_MESSAGE);
	}
	
	@Test
	@DisplayName("Should throw IllegalArgumentException if the attribute 'dueDate' is invalid")
	void updateTaskTest01() {
		// arrange
		Task task = new Task("Title", "description", LocalDate.now(), null);
		
		// assert
		Assertions.assertDoesNotThrow(() -> task.updateTask("Title", null, LocalDate.now().plusDays(20L), null));
	
		Assertions.assertThrows(
				IllegalArgumentException.class,
				() -> task.updateTask("update", null, LocalDate.now().minusDays(10L), null),
				DUE_DATE_VALIDATION_EXCEPTION_MESSAGE);
	}

	@Test
	@DisplayName("Should update attributes")
	void updateTaskTest02() {
		// arrange
		Task task = new Task("Title", "description", LocalDate.now(), null);
		
		// arrange
		String TITLE_UPDATED = "title-updated";
		String DESCRIPTION_UPDATED = "description-updated";
		LocalDate DUE_DATE_UPDATED = LocalDate.now().plusDays(100L);
		StatusTask STATUS_UPDATED = StatusTask.CONCLUDED;

		// act
		task.updateTask(TITLE_UPDATED, DESCRIPTION_UPDATED, DUE_DATE_UPDATED, STATUS_UPDATED);
		
		// assert
		Assertions.assertEquals(task.getTitle(), TITLE_UPDATED);
		Assertions.assertEquals(task.getDescription(), DESCRIPTION_UPDATED);
		Assertions.assertEquals(task.getDueDate(), DUE_DATE_UPDATED);
		Assertions.assertEquals(task.getStatus(), STATUS_UPDATED);
	}
	
	@Test
	@DisplayName("Should not update attributes")
	void updateTaskTest05() {
		// arrange
		Task task = new Task("Title", "description", LocalDate.now(), null);
		
		String TITLE_UPDATED = null;
		String DESCRIPTION_UPDATED = null;
		LocalDate DUE_DATE_UPDATED = null;
		StatusTask STATUS_UPDATED = null;
		
		// act
		task.updateTask(TITLE_UPDATED, DESCRIPTION_UPDATED, DUE_DATE_UPDATED, STATUS_UPDATED);
		
		// assert
		Assertions.assertNotEquals(task.getTitle(), TITLE_UPDATED);
		Assertions.assertNotEquals(task.getDescription(), DESCRIPTION_UPDATED);
		Assertions.assertNotEquals(task.getDueDate(), DUE_DATE_UPDATED);
		Assertions.assertNotEquals(task.getStatus(), STATUS_UPDATED);
		Assertions.assertNotNull(task.getTitle());
		Assertions.assertNotNull(task.getDescription());
		Assertions.assertNotNull(task.getDueDate());
		Assertions.assertNotNull(task.getStatus());
	}
}