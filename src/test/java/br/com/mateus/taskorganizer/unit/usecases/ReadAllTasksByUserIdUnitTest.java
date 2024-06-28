package br.com.mateus.taskorganizer.unit.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.mateus.taskorganizer.application.gateways.TaskRepository;
import br.com.mateus.taskorganizer.application.usecases.task.ReadAllTasksByUserId;
import br.com.mateus.taskorganizer.domain.task.Task;
import br.com.mateus.taskorganizer.tests_utils.TaskUtils;

@ExtendWith(MockitoExtension.class)
class ReadAllTasksByUserIdUnitTest {
	
	@Mock
	private TaskRepository repository;

	@InjectMocks 
	private ReadAllTasksByUserId readAllTasksByUserId;
	private final Random random = new Random();
	
	
	@Test
	@DisplayName("Should return a list of Task with correct attributes")
	void listTasksTest() {
	    // arrange
	    List<Task> tasks = List.of(
			TaskUtils.getRandomDefaultTaskWithoutId(),
			TaskUtils.getRandomDefaultTaskWithoutId()
	    );
	    when(repository.getAllTasksByUserId(anyLong())).thenReturn(tasks);
	    
	    // act
	    List<Task> result = readAllTasksByUserId.getAllTasksByUserId(random.nextLong());
	    
	    // assert
	    assertNotNull(result, "The result list should not be null");
	    assertNotEquals(0, result.size(), "The result list should not be empty");
	    assertEquals(2, result.size(), "The result list should contain 2 items");

	    Map<String, Task> taskMap = tasks.stream().collect(Collectors.toMap(Task::getTitle, t -> t));
	    result.forEach(task -> {
	        Task taskMapped = taskMap.get(task.getTitle());
	        
	        assertNotNull(taskMapped, "The mapped task should not be null");
	        assertEquals(taskMapped.getDescription(), task.getDescription(), "The description should match the task description");
	        assertEquals(taskMapped.getDueDate(), task.getDueDate(), "The due date should match the task due date");
	    });
	}
}