package br.com.mateus.taskorganizer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mateus.taskorganizer.model.task.TaskDTO;
import br.com.mateus.taskorganizer.model.task.TaskService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/organizer/tasks")
public class TaskController {

	@Autowired
	private TaskService service;
	
	
	@PostMapping("/register-task")
	@Transactional
	public void registerTask(@RequestBody @Valid TaskDTO dto) {
		service.registerTask(dto);
	}

	@GetMapping("/all")
	public List<TaskDTO> listTasks() {
		return service.listTasks();
	}
	
	@PutMapping("/update")
	@Transactional
	public void updateTask(@RequestBody TaskDTO dto) {
		service.updateTask(dto);
	}

	@GetMapping("/task/{id}")
	public TaskDTO showTask(@PathVariable Long id) {
		return service.showTask(id);
	}
	
	@DeleteMapping("/remove/{id}")
	@Transactional
	public void removeTask(@PathVariable Long id) {
		service.removeTask(id);
	}
}
