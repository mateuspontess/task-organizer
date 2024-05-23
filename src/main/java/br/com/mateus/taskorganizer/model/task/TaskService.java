package br.com.mateus.taskorganizer.model.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.mateus.taskorganizer.model.user.User;

@Service
public class TaskService {
	
	@Autowired
	private TaskRepository taskRepository;
	
	public Task registerTask(TaskCreateDTO dto) {
		Task task = new Task(dto.title(), dto.description(), dto.dueDate(), this.getUser());
		return taskRepository.save(task);
	}

	public List<TaskResponseDTO> listTasks() {
		User user = this.getUser();
		return taskRepository.findAllByUserId(user.getId()).stream()
				.map(t -> new TaskResponseDTO(t)).toList();
	}
	
	public Task updateTask(Long taskId, TaskUpdateDTO dto) {
		Task task = taskRepository.findByIdAndUserId(taskId, this.getUser().getId());
		StatusTask status = dto.status() != null &&  !dto.status().isBlank() ? StatusTask.fromString(dto.status()) : null;
		task.updateTask(dto.title(), dto.description(), dto.dueDate(), status);

		return this.taskRepository.save(task);
	}
	
	public TaskResponseDTO showTask(Long taskId) {
		Task task = taskRepository.findByIdAndUserId(taskId, this.getUser().getId());
		return new TaskResponseDTO(task);
	}
	
	public void removeTask(Long taskId) {
		taskRepository.deleteByUserIdAndId(this.getUser().getId(), taskId);
	}
	
	
	private User getUser() {
		return (User) SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal();
	}
}