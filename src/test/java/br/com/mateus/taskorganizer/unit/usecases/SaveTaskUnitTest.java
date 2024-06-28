package br.com.mateus.taskorganizer.unit.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.mateus.taskorganizer.application.gateways.TaskRepository;
import br.com.mateus.taskorganizer.application.usecases.task.SaveTask;
import br.com.mateus.taskorganizer.domain.task.Task;
import br.com.mateus.taskorganizer.tests_utils.TaskUtils;

@ExtendWith(MockitoExtension.class)
class SaveTaskUnitTest {
	
	@Mock
	private TaskRepository repository;

	@InjectMocks 
	private SaveTask saveTask;
	
	
	@Test
	@DisplayName("Should return a list of TaskResponseDTO with correct attributes")
	void listTasksTest() {
	    // arrange
		Task taskMock = TaskUtils.getRandomDefaultTaskWithoutId();
		when(repository.saveTask(any())).thenReturn(taskMock);
	    
	    // act
	    Task result = saveTask.registerTask(taskMock);
	    
	    // assert
	    assertNotNull(result, "The result should not be null");
		assertNotNull(result, "The mapped task should not be null");
		assertEquals(taskMock.getDescription(), result.getDescription(), "The description should match the task description");
		assertEquals(taskMock.getDueDate(), result.getDueDate(), "The due date should match the task due date");
	}
}