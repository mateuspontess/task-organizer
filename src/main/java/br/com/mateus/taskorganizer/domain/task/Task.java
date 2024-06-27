package br.com.mateus.taskorganizer.domain.task;

import java.time.LocalDate;

public class Task {

	private Long id;
	private String title;
	private String description;
	private LocalDate dueDate;
	private StatusTask status;
	private Long userId;
	

	public Task() {}

	public Task(Long id, String title, String description, LocalDate dueDate, StatusTask status, Long userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
        this.userId = userId;
    }
	
	public Task(String title, String description, LocalDate dueDate, Long userId) {
		this.validateTitle(title);
		this.validateDueDate(dueDate);
		this.title = title;
		this.dueDate = dueDate;
		
		this.description = description;
		this.userId = userId;
		this.status = StatusTask.PENDING;
	}
	
	public void updateTask(String title, String description, LocalDate dueDate, StatusTask status) {
		if (title != null && !title.isBlank())
			this.title = title;
		
		if (description != null && !description.isBlank())
			this.description = description;
		
		if (status != null)
			this.status = status;
		
		this.validateDueDate(dueDate);
		if (dueDate != null)
			this.dueDate = dueDate;
	}
	
	private void validateTitle(String title) {
		if (title == null || title.isBlank())
			throw new IllegalArgumentException("Title cannot be null or blank");
	}
	private void validateDueDate(LocalDate dueDate) {
		if (dueDate != null && dueDate.isBefore(LocalDate.now()))
			throw new IllegalArgumentException("Due date cannot be in the past");
	}

	
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public StatusTask getStatus() {
		return status;
	}

	public Long getUserId() {
		return userId;
	}
}