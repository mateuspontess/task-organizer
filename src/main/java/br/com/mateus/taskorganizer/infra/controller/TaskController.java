package br.com.mateus.taskorganizer.infra.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.mateus.taskorganizer.application.dto.task.input.TaskCreateDTO;
import br.com.mateus.taskorganizer.application.dto.task.input.TaskUpdateDTO;
import br.com.mateus.taskorganizer.application.dto.task.output.TaskResponseDTO;
import br.com.mateus.taskorganizer.application.usecases.task.ReadAllTasksByUserId;
import br.com.mateus.taskorganizer.application.usecases.task.ReadTaskByIdAndUserId;
import br.com.mateus.taskorganizer.application.usecases.task.RemoveTaskByIdAndUserId;
import br.com.mateus.taskorganizer.application.usecases.task.SaveTask;
import br.com.mateus.taskorganizer.application.usecases.task.UpdateTask;
import br.com.mateus.taskorganizer.domain.task.Task;
import br.com.mateus.taskorganizer.infra.persistence.user.UserEntity;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
@SecurityRequirement(name = "bearer-key")
public class TaskController {

	@Autowired
	private SaveTask createTask;
	@Autowired
	private ReadAllTasksByUserId readAllTasksByUserId;
	@Autowired
	private ReadTaskByIdAndUserId readTaskByIdAndUserId;
	@Autowired
	private UpdateTask updateTask;
	@Autowired
	private RemoveTaskByIdAndUserId removeTaskByIdAndUserId;
	
	
	@Transactional
	@PostMapping
	public ResponseEntity<TaskResponseDTO> registerTask(
		@RequestBody @Valid TaskCreateDTO dto, 
		UriComponentsBuilder uriBuilder,
		Authentication auth
	) {
		UserEntity currentUser = (UserEntity) auth.getPrincipal();
		Task task = new Task(dto.title(), dto.description(), dto.dueDate(), currentUser.getId());
		task = createTask.registerTask(task);
		
		var uri = uriBuilder.path("/tasks/{id}").buildAndExpand(task.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new TaskResponseDTO(task));
	}

	@GetMapping
	public ResponseEntity<List<TaskResponseDTO>> listTasks(Authentication auth) {
		UserEntity currentUser = (UserEntity) auth.getPrincipal();

		return ResponseEntity.ok(readAllTasksByUserId.getAllTasksByUserId(currentUser.getId()).stream()
			.map(TaskResponseDTO::new)
			.toList());
	}
	
	@GetMapping("/{taskId}")
	public ResponseEntity<TaskResponseDTO> showTask(
		@PathVariable String taskId,
		Authentication auth
	) {
		UserEntity currentUser = (UserEntity) auth.getPrincipal();
		return ResponseEntity.ok(new TaskResponseDTO(readTaskByIdAndUserId.getTaskByIdAndUserId(taskId, currentUser.getId())));
	}
	
	@Transactional
	@PutMapping("/{taskId}")
	public ResponseEntity<TaskResponseDTO> updateTask(
		@PathVariable String taskId, 
		@RequestBody TaskUpdateDTO dto,
		Authentication auth
	) {
		UserEntity currentUser = (UserEntity) auth.getPrincipal();
		return ResponseEntity.ok(new TaskResponseDTO(updateTask.updateTaskData(taskId, currentUser.getId(), dto)));
	}
	
	@Transactional
	@DeleteMapping("/{taskId}")
	public ResponseEntity<TaskResponseDTO> removeTask(
		@PathVariable String taskId,
		Authentication auth
	) {
		UserEntity currentUser = (UserEntity) auth.getPrincipal();
		removeTaskByIdAndUserId.deleteTaskByIdAndUserId(taskId, currentUser.getId());
		return ResponseEntity.noContent().build();
	}
}