package br.com.mateus.taskorganizer.model.task;

import java.nio.file.AccessDeniedException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.mateus.taskorganizer.model.user.User;

@Service
public class TaskService {
//	private static final Logger logger = LoggerFactory.getLogger(TaskService.class);
	
	@Autowired
	private TaskRepository taskRepository;
	
	public Task registerTask(TaskRequestDTO dto) {
		User user = this.getUser();
		Task task = new Task(dto.title(), dto.description(), dto.dueDate(), dto.status(), user);
		taskRepository.save(task);
		
		return task;
	}

	public List<TaskResponseDTO> listTasks() {
		User user = this.getUser();
		return taskRepository.findAllByUserId(user.getId()).stream().map(t -> new TaskResponseDTO(t)).toList();
	}
	
	public Task updateTask(TaskRequestDTO dto) throws AccessDeniedException {
		User user = this.getUser();
		Task task = taskRepository.getReferenceById(dto.id());
		this.accesVerify(user, task);
		
		task.updateTask(dto.title(), dto.description(), dto.dueDate(), dto.status());
		return task;
	}
	
	public TaskResponseDTO showTask(Long id) throws AccessDeniedException {
		User user = this.getUser();
		Task task = taskRepository.getReferenceById(id);
		this.accesVerify(user, task);
		
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
	private void accesVerify(User user, Task task) throws AccessDeniedException {
		if (user == null || !task.getUser().getId().equals(user.getId())) throw new AccessDeniedException("User do not have permission for access this task");
	}
}

