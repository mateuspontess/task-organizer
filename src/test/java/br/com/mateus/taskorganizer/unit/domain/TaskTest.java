package br.com.mateus.taskorganizer.unit.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Random;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.mateus.taskorganizer.domain.task.StatusTask;
import br.com.mateus.taskorganizer.domain.task.Task;

class TaskTest {

	private final Random random = new Random();
	private final LocalDate PAST = LocalDate.now().minusDays(20);
	
	@Test
	@DisplayName("All args constructor - Should throw IllegalArgumentException if attribute 'title' is invalid")
	void testConstructorWithAllFields() {
		assertThrows(IllegalArgumentException.class,
			() -> new Task(null, "title", "description", LocalDate.now(), StatusTask.PENDING, random.nextLong()),
			"Expected IllegalArgumentException to be thrown when id is null");

		assertThrows(IllegalArgumentException.class,
			() -> new Task(random.nextLong(), "", "description", LocalDate.now(), StatusTask.PENDING, random.nextLong()),
			"Expected IllegalArgumentException to be thrown when blank is null");
		assertThrows(IllegalArgumentException.class,
			() -> new Task(random.nextLong(), null, "description", LocalDate.now(), StatusTask.PENDING, random.nextLong()),
			"Expected IllegalArgumentException to be thrown when title is null");

		assertDoesNotThrow(
			() -> new Task(random.nextLong(), "title", "", LocalDate.now(), StatusTask.PENDING, random.nextLong()),
			"No exception should be thrown when description is null");
		assertDoesNotThrow(
			() -> new Task(random.nextLong(), "title", null, LocalDate.now(), StatusTask.PENDING, random.nextLong()),
			"No exception should be thrown when description is blank");

		assertDoesNotThrow(
			() -> new Task(random.nextLong(), "title", "description", null, StatusTask.PENDING, random.nextLong()),
			"No exception should be thrown when dueDate is null");
		assertThrows(IllegalArgumentException.class,
			() -> new Task(random.nextLong(), "title", "description", PAST, StatusTask.PENDING, random.nextLong()),
			"Expected IllegalArgumentException to be thrown when dueDate is in the past");

		assertThrows(IllegalArgumentException.class,
			() -> new Task(random.nextLong(), "title", "description", LocalDate.now(), null, random.nextLong()),
			"Expected IllegalArgumentException to be thrown when status is null");

		assertThrows(IllegalArgumentException.class,
			() -> new Task(random.nextLong(), "title", "description", LocalDate.now(), StatusTask.PENDING, null),
			"Expected IllegalArgumentException to be thrown when userId is null");
	}
	@Test
	@DisplayName("Required fields constructor - Should throw IllegalArgumentException if attributes is invalid")
	void testConstructorWithRequiredFields() {
		assertThrows(IllegalArgumentException.class,
			() -> new Task(null, "description", LocalDate.now(), random.nextLong()),
			"Expected IllegalArgumentException to be thrown when title is null");
		assertThrows(IllegalArgumentException.class,
			() -> new Task("", "description", LocalDate.now(), random.nextLong()),
			"Expected IllegalArgumentException to be thrown when title is blank");

		assertDoesNotThrow( () -> new Task("title", "", LocalDate.now(), random.nextLong()),
			"No exception should be thrown when description is blank");
		assertDoesNotThrow( () -> new Task("title", null, LocalDate.now(), random.nextLong()),
			"No exception should be thrown when description is null");

		assertDoesNotThrow(() -> new Task("title", "description", null, random.nextLong()),
			"No exception should be thrown when dueDate is null");
		assertThrows(IllegalArgumentException.class,
			() -> new Task("title", "description", LocalDate.now().minusDays(10), random.nextLong()),
			"Expected IllegalArgumentException to be thrown when dueDate is in the past");

		assertThrows(IllegalArgumentException.class,
			() -> new Task("title", "description", LocalDate.now(), null),
			"Expected IllegalArgumentException to be thrown when userId is null");
	}
	

	@Test
	@DisplayName("Should throw IllegalArgumentException if the attribute 'dueDate' is in the past")
	void updateTaskTest01() {
		// arrange
		Task task = this.getValidTask();
		
		// act and assert
		assertDoesNotThrow(() -> task.updateTask("Title", null, LocalDate.now(), null));
	
		assertThrows(
			IllegalArgumentException.class,
			() -> task.updateTask("update", null, PAST, null),
			"Expected IllegalArgumentException to be thrown when dueDate is in the past");
	}
	@Test
	@DisplayName("Should update task attributes correctly")
	void updateTaskTest02() {
	    // arrange
		Task task = this.getValidTask();
	    
	    String TITLE_UPDATED = "title-updated";
	    String DESCRIPTION_UPDATED = "description-updated";
	    LocalDate DUE_DATE_UPDATED = LocalDate.now().plusDays(100L);
	    StatusTask STATUS_UPDATED = StatusTask.CONCLUDED;

	    // act
	    task.updateTask(TITLE_UPDATED, DESCRIPTION_UPDATED, DUE_DATE_UPDATED, STATUS_UPDATED);
	    
	    // assert
	    assertEquals(task.getTitle(), TITLE_UPDATED, "The title should be updated");
	    assertEquals(task.getDescription(), DESCRIPTION_UPDATED, "The description should be updated");
	    assertEquals(task.getDueDate(), DUE_DATE_UPDATED, "The due date should be updated");
	    assertEquals(task.getStatus(), STATUS_UPDATED, "The status should be updated");
	}
	@Test
	@DisplayName("Should not update any fields")
	void updateTaskTest03() {
		// arrange
		Task task = this.getValidTask();
		
		// act
		assertDoesNotThrow(() -> task.updateTask("", "", null, null));

		// assert
		assertFalse(task.getTitle().isBlank(), "The title should not be blank");
		assertFalse(task.getDescription().isBlank(), "The description should not be null");
		assertNotNull(task.getDueDate(), "The due date should not be null");
		assertNotNull(task.getStatus(), "The status should not be null");
	}
	@Test
	@DisplayName("Should not update task attributes when all new values are null")
	void updateTaskTest04() {
		// arrange
		Task task = this.getValidTask();
		System.out.println("AQUI: " +  task);
	
		// act
		assertDoesNotThrow(() -> task.updateTask(null, null, null, null));

	    // assert
	    assertNotNull(task.getTitle(), "The title should not be null");
	    assertNotNull(task.getDescription(), "The description should not be null");
	    assertNotNull(task.getDueDate(), "The due date should not be null");
	    assertNotNull(task.getStatus(), "The status should not be null");
	}

	@Test
	void removeDueDateTest() {
		// arrange
		Task task = this.getValidTask();

		// act
		task.removeDueDate();

		// assert
		assertNull(task.getDueDate());
	}
	@Test
	void removeDescriptionTest() {
		// arrange
		Task task = this.getValidTask();

		// act
		task.removeDescription();

		// assert
		assertTrue(task.getDescription().isBlank());
	}

	private Task getValidTask() {
		return new Task("Title", "description", LocalDate.now(), random.nextLong());
	}
}