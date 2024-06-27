package br.com.mateus.taskorganizer.infra.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.mateus.taskorganizer.application.dto.task.TaskCreateDTO;
import br.com.mateus.taskorganizer.application.dto.task.TaskResponseDTO;
import br.com.mateus.taskorganizer.application.dto.task.TaskUpdateDTO;
import br.com.mateus.taskorganizer.application.usecases.task.ReadAllTasksByUserId;
import br.com.mateus.taskorganizer.application.usecases.task.ReadTaskByIdAndUserId;
import br.com.mateus.taskorganizer.application.usecases.task.RemoveTaskByIdAndUserId;
import br.com.mateus.taskorganizer.application.usecases.task.SaveTask;
import br.com.mateus.taskorganizer.application.usecases.task.UpdateTask;
import br.com.mateus.taskorganizer.domain.task.Task;
import br.com.mateus.taskorganizer.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
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
	
	
	@PostMapping
	@Transactional
	public ResponseEntity<TaskResponseDTO> registerTask(@RequestBody @Valid TaskCreateDTO dto, UriComponentsBuilder uriBuilder) {
		Task task = new Task(dto.title(), dto.description(), dto.dueDate(), SecurityUtils.getUserId());
		task = createTask.registerTask(task);
		
		var uri = uriBuilder.path("/tasks/{id}").buildAndExpand(task.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new TaskResponseDTO(task));
	}

	@GetMapping
	public ResponseEntity<List<TaskResponseDTO>> listTasks() {
		return ResponseEntity.ok(readAllTasksByUserId.getAllTasksByUserId(SecurityUtils.getUserId()).stream()
			.map(TaskResponseDTO::new)
			.toList());
	}
	
	@PutMapping("/{taskId}")
	@Transactional
	public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable Long taskId, @RequestBody TaskUpdateDTO dto) {
		return ResponseEntity.ok(new TaskResponseDTO(updateTask.updateTaskData(taskId, SecurityUtils.getUserId(), dto)));
	}

	@GetMapping("/{taskId}")
	public ResponseEntity<TaskResponseDTO> showTask(@PathVariable Long taskId) {
		return ResponseEntity.ok(new TaskResponseDTO(readTaskByIdAndUserId.getTaskByIdAndUserId(taskId, SecurityUtils.getUserId())));
	}
	
	@DeleteMapping("/{taskId}")
	@Transactional
	public ResponseEntity<TaskResponseDTO> removeTask(@PathVariable Long taskId) {
		removeTaskByIdAndUserId.deleteTaskByIdAndUserId(taskId, SecurityUtils.getUserId());
		return ResponseEntity.noContent().build();
	}
}