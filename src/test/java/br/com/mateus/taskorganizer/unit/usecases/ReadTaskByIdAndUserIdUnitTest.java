package br.com.mateus.taskorganizer.unit.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Random;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.mateus.taskorganizer.application.gateways.TaskRepository;
import br.com.mateus.taskorganizer.application.usecases.task.ReadTaskByIdAndUserId;
import br.com.mateus.taskorganizer.domain.task.Task;
import br.com.mateus.taskorganizer.tests_utils.TaskUtils;

@ExtendWith(MockitoExtension.class)
class ReadTaskByIdAndUserIdUnitTest {
	
	@Mock
	private TaskRepository repository;

	@InjectMocks 
	private ReadTaskByIdAndUserId readTaskByIdAndUserId;
	private final Random random = new Random();

	
	@Test
	@DisplayName("Should return a list of TaskResponseDTO with correct attributes")
	void listTasksTest() {
	    // arrange
		Task taskMock = TaskUtils.getRandomDefaultTaskWithoutId();
		when(repository.getTaskByIdAndUserId(anyLong(), anyLong())).thenReturn(taskMock);
	    
	    // act
	    Task result = readTaskByIdAndUserId.getTaskByIdAndUserId(random.nextLong(), random.nextLong());
	    
	    // assert
	    assertNotNull(result, "The result should not be null");
		assertEquals(taskMock.getTitle(), result.getTitle());
		assertEquals(taskMock.getDescription(), result.getDescription());
		assertEquals(taskMock.getDueDate(), result.getDueDate());
		assertEquals(taskMock.getUserId(), result.getUserId());
	}
}