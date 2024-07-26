package br.com.mateus.taskorganizer.infra.persistence.task;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.mateus.taskorganizer.domain.task.StatusTask;
import br.com.mateus.taskorganizer.domain.task.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tasks")
public class TaskEntity {
	
	@Setter
	@Id
	private String id;
    private String title;
    private String description;
    private LocalDate dueDate;
	private StatusTask status;
	private String userId;

	
	public TaskEntity(
		String title, 
		String description, 
		LocalDate dueDate, 
		String userId
	) {
        this.title = title;
        this.dueDate = dueDate;
		this.title = title;
		this.dueDate = dueDate;
		this.description = description;
		this.userId = userId;
		this.status = StatusTask.PENDING;
	}

	public TaskEntity(Task task) {
		this.id = task.getId();
		this.title = task.getTitle();
		this.description = task.getDescription();
		this.dueDate = task.getDueDate();
		this.status = task.getStatus();
		this.userId = task.getUserId();
	}
}