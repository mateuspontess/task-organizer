package br.com.mateus.taskorganizer.unit.model.task;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.mateus.taskorganizer.model.task.Task;
import br.com.mateus.taskorganizer.model.task.TaskCreateDTO;
import br.com.mateus.taskorganizer.model.task.TaskRepository;
import br.com.mateus.taskorganizer.model.task.TaskResponseDTO;
import br.com.mateus.taskorganizer.model.task.TaskService;
import br.com.mateus.taskorganizer.model.task.TaskUpdateDTO;
import br.com.mateus.taskorganizer.model.user.User;
import br.com.mateus.taskorganizer.model.user.UserRole;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
	
	@Mock
	private TaskRepository repository;
	
	@Mock
	private SecurityContext securityContext;
	
	@Mock
	private Authentication authentication;
	
	@InjectMocks 
	private TaskService service;
	
	@Captor
	private ArgumentCaptor<Task> taskCaptor;
	
	@BeforeEach
	public void setUp() {
		SecurityContextHolder.setContext(securityContext);
		User user = new User(1L, "root", "root", UserRole.ADMIN, null);
		when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
	}

	
	@Test
	@DisplayName("Must create a Task with the same attributes as the DTO")
	void registerTaskTest() {
	    // arrange
	    TaskCreateDTO dto = new TaskCreateDTO("title", "description", LocalDate.now());

	    // act
	    service.registerTask(dto);
	    
	    // assert
	    verify(repository).save(taskCaptor.capture());
	    Task taskCaptured = taskCaptor.getValue();
	    
	    Assertions.assertEquals(taskCaptured.getTitle(), dto.title(), "The title of the task should match the title in the DTO");
	    Assertions.assertEquals(taskCaptured.getDescription(), dto.description(), "The description of the task should match the description in the DTO");
	    Assertions.assertEquals(taskCaptured.getDueDate(), dto.dueDate(), "The due date of the task should match the due date in the DTO");
	}
	
	@Test
	@DisplayName("Should return a list of TaskResponseDTO with correct attributes")
	void listTasksTest() {
	    // arrange
	    List<Task> tasks = List.of(
	            new Task("title-1", "description-1", LocalDate.now(), new User()),
	            new Task("title-2", "description-2", LocalDate.now(), new User())
	    );
	    when(repository.findAllByUserId(anyLong())).thenReturn(tasks);
	    
	    // act
	    List<TaskResponseDTO> result = service.listTasks();
	    
	    // assert
	    assertNotNull(result, "The result list should not be null");
	    Assertions.assertNotEquals(0, result.size(), "The result list should not be empty");
	    Assertions.assertEquals(2, result.size(), "The result list should contain 2 items");

	    Map<String, Task> taskMap = tasks.stream().collect(Collectors.toMap(Task::getTitle, t -> t));
	    result.forEach(dto -> {
	        Task taskMapped = taskMap.get(dto.title());
	        
	        Assertions.assertNotNull(taskMapped, "The mapped task should not be null");
	        Assertions.assertEquals(taskMapped.getDescription(), dto.description(), "The description should match the task description");
	        Assertions.assertEquals(taskMapped.getDueDate(), dto.dueDate(), "The due date should match the task due date");
	    });
	}
	
	@Test
	@DisplayName("Should update and return the Task with the new values")
	void updateTaskTest() {
	    // arrange
	    Task task = new Task("title", "description", LocalDate.now(), new User());

	    Long taskId = 1L;
	    TaskUpdateDTO dto = new TaskUpdateDTO(
	            "title-update", 
	            "description-updated", 
	            LocalDate.now().plusDays(1L),
	            "CONCLUDED");
	    
	    when(repository.findByIdAndUserId(anyLong(), anyLong())).thenReturn(task);
	    
	    // act
	    service.updateTask(taskId, dto);
	    
	    // assert
	    verify(repository).save(taskCaptor.capture());
	    Task taskCaptured = taskCaptor.getValue();
	    
	    Assertions.assertEquals(taskCaptured.getTitle(), dto.title(), "The title should be updated to the new value");
	    Assertions.assertEquals(taskCaptured.getDescription(), dto.description(), "The description should be updated to the new value");
	    Assertions.assertEquals(taskCaptured.getDueDate(), dto.dueDate(), "The due date should be updated to the new value");
	    Assertions.assertEquals(taskCaptured.getStatus().toString(), dto.status(), "The status should be updated to the new value");
	}

	
	@Test
	@DisplayName("Should return a TaskResponseDTO with the same attributes as Task")
	void showTaskTest() {
	    // arrange
	    Task task = new Task("title-1", "description-1", LocalDate.now(), new User());
	    when(repository.findByIdAndUserId(anyLong(), anyLong())).thenReturn(task);
	    
	    // act
	    TaskResponseDTO dto = service.showTask(1L);
	    
	    // assert
	    assertNotNull(dto, "The TaskResponseDTO should not be null");
	    Assertions.assertEquals(task.getTitle(), dto.title(), "The title should match the task's title");
	    Assertions.assertEquals(task.getDescription(), dto.description(), "The description should match the task's description");
	    Assertions.assertEquals(task.getDueDate(), dto.dueDate(), "The due date should match the task's due date");
	}

}