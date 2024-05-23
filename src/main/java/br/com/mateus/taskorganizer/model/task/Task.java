package br.com.mateus.taskorganizer.model.task;

import java.time.LocalDate;

import br.com.mateus.taskorganizer.model.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Entity(name = "Task")
@Table(name = "tasks")
@EqualsAndHashCode(of = "id")
public class Task {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter
	private Long id;
	
	@Column
	private String title;
	
	private String description;
	private LocalDate dueDate;
	
	@Enumerated(EnumType.STRING)
	private StatusTask status;
	
	@ManyToOne
	private User user;
	
	
	public Task(String title, String description, LocalDate dueDate, User user) {
		this.title = title;
		this.description = description;
		this.dueDate = dueDate;
		this.status = StatusTask.PENDING;
		this.user = user;
	}
	public void updateTask(String title, String description, LocalDate dueDate, StatusTask status) {
		if (title != null && title.isBlank())
			throw new IllegalArgumentException("Title cannot be empty");
		if (dueDate != null && dueDate.isBefore(LocalDate.now()))
			throw new IllegalArgumentException("Due date cannot be in the past");
		
		this.title = title;
		this.dueDate = dueDate;
		
		this.description = description != null ? description : this.description;
		this.status = status != null ? status : this.status;
	}
}