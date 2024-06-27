package br.com.mateus.taskorganizer.infra.persistence.task;

import java.time.LocalDate;

import br.com.mateus.taskorganizer.domain.task.StatusTask;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tasks")
public class TaskEntity {
	
	@Setter
	@Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    private String title;
    private String description;
    private LocalDate dueDate;

	@Enumerated(EnumType.STRING)
	private StatusTask status;
	
	private Long userId;

	
	public TaskEntity(String title, String description, LocalDate dueDate, Long userId) {
        this.title = title;
        this.dueDate = dueDate;
		this.title = title;
		this.dueDate = dueDate;
		this.description = description;
		this.userId = userId;
		this.status = StatusTask.PENDING;
	}
}