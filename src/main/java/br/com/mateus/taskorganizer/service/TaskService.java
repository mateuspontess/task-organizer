package br.com.mateus.taskorganizer.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mateus.taskorganizer.dto.TaskDTO;
import br.com.mateus.taskorganizer.model.Task;
import br.com.mateus.taskorganizer.repository.TaskRepository;

@Service
public class TaskService {
	
	@Autowired
	private TaskRepository rp;
	
	public void registerTask(TaskDTO dto) {
		rp.save(new Task(dto.title(), dto.description(), dto.dueDate(), dto.status()));
	};

	public List<TaskDTO> listTasks() {
		return rp.findAll().stream().map(t -> new TaskDTO(t.getId(), t.getTitle(), t.getDescription(), t.getDueDate(), t.getStatus())).toList();
	}
	
	public void updateTask(TaskDTO dto) {
		Task task = rp.getReferenceById(dto.id());
		task.atualizaTarefa(dto.title(), dto.description(), dto.dueDate(), dto.status());
	}
	
	public TaskDTO showTask(Long id) {
		Optional<Task> optTask = rp.findById(id);
		if (optTask.isPresent()) {
			Task task = optTask.get();
			return new TaskDTO(task.getId(), task.getTitle(), task.getDescription(), task.getDueDate(), task.getStatus());
		}
		throw new IllegalStateException("No task was found with this ID");
	}
	
	public void removeTask(Long id) {
		rp.deleteById(id);
	}
}
