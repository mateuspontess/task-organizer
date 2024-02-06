package br.com.mateus.taskorganizer.model.task;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity(name = "Task")
@Table(name = "tasks")
@EqualsAndHashCode(of = "id")
public class Task {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String title;
	private String description;
	private LocalDate dueDate;
	@Enumerated(EnumType.STRING)
	private StatusTask status;
	
	public Task(String title, String description, LocalDate dueDate, StatusTask status) {
		this.title = title;
		this.description = description;
		this.dueDate = dueDate;
		this.status = status;
	}
	public void updateTask(String title, String description, LocalDate dueDate, StatusTask status) {
		this.title = title;
		this.description = description;
		this.dueDate = dueDate;
		this.status = status;
	}
}
