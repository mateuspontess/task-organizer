package br.com.mateus.taskorganizer.controller;

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

import br.com.mateus.taskorganizer.model.task.Task;
import br.com.mateus.taskorganizer.model.task.TaskCreateDTO;
import br.com.mateus.taskorganizer.model.task.TaskResponseDTO;
import br.com.mateus.taskorganizer.model.task.TaskService;
import br.com.mateus.taskorganizer.model.task.TaskUpdateDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
@SecurityRequirement(name = "bearer-key")
public class TaskController {

	@Autowired
	private TaskService service;
	
	
	@PostMapping
	@Transactional
	public ResponseEntity<TaskResponseDTO> registerTask(@RequestBody @Valid TaskCreateDTO dto, UriComponentsBuilder uriBuilder) {
		Task task = service.registerTask(dto);
		
		var uri = uriBuilder.path("/tasks/{id}").buildAndExpand(dto).toUri();
		
		return ResponseEntity.created(uri).body(new TaskResponseDTO(task));
	}

	@GetMapping
	public ResponseEntity<List<TaskResponseDTO>> listTasks() {
		return ResponseEntity.ok(service.listTasks());
	}
	
	@PutMapping("/{taskId}")
	@Transactional
	public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable Long taskId, @RequestBody TaskUpdateDTO dto) {
		Task task = service.updateTask(taskId, dto);
		return ResponseEntity.ok(new TaskResponseDTO(task));
	}

	@GetMapping("/{id}")
	public ResponseEntity<TaskResponseDTO> showTask(@PathVariable Long id) {
		return ResponseEntity.ok(service.showTask(id));
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<TaskResponseDTO> removeTask(@PathVariable Long id) {
		service.removeTask(id);
		return ResponseEntity.noContent().build();
	}
}