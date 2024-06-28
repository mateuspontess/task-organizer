package br.com.mateus.taskorganizer.unit.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.mateus.taskorganizer.application.dto.task.input.TaskUpdateDTO;
import br.com.mateus.taskorganizer.application.gateways.TaskRepository;
import br.com.mateus.taskorganizer.application.usecases.task.UpdateTask;
import br.com.mateus.taskorganizer.domain.task.StatusTask;
import br.com.mateus.taskorganizer.domain.task.Task;
import br.com.mateus.taskorganizer.tests_utils.TaskUtils;

@ExtendWith(MockitoExtension.class)
class UpdateTaskUnitTest {
	
	@Mock
	private TaskRepository repository;

	@InjectMocks 
	private UpdateTask updateTask;

	
	@Test
	@DisplayName("Should return a list of TaskResponseDTO with correct attributes")
	void listTasksTest() {
	    // arrange
		Task taskMock = TaskUtils.getRandomDefaultTaskWithoutId();
		when(repository.getTaskByIdAndUserId(anyLong(), anyLong())).thenReturn(taskMock);
		when(repository.saveTask(any())).thenReturn(taskMock);
	    
	    // act
		Long ficTaskId = 1L;
		Long ficUserId = 1L; 
		TaskUpdateDTO dto = new TaskUpdateDTO("updated", "updated", LocalDate.now().plusDays(20), StatusTask.CONCLUDED);
	    Task result = updateTask.updateTaskData(ficTaskId, ficUserId, dto);
	    
	    // assert
	    assertNotNull(result, "The result should not be null");

		assertEquals(taskMock.getTitle(), result.getTitle(), "The due date should match the task due date");
		assertEquals(taskMock.getDescription(), result.getDescription(), "The description should match the task description");
		assertEquals(taskMock.getDueDate(), result.getDueDate(), "The due date should match the task due date");
		assertEquals(taskMock.getStatus(), result.getStatus(), "The due date should match the task due date");
	}
}