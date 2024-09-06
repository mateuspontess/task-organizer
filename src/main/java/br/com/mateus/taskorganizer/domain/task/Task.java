package br.com.mateus.taskorganizer.domain.task;

import java.time.LocalDate;

public class Task {

	private String id;
	private String title;
	private String description;
	private LocalDate dueDate;
	private StatusTask status;
	private String userId;
	

	public Task() {}

	public Task(String id, String title, String description, LocalDate dueDate, StatusTask status, String userId) {
		this.cannotBeNull(id, "id");
		this.cannotBeBlank(title, "title");
		this.cannotBeInThePast(dueDate);
		this.cannotBeNull(status, "status");
		this.cannotBeNull(userId, "userId");

        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
        this.userId = userId;
    }
	
	public Task(String title, String description, LocalDate dueDate, String userId) {
		this.cannotBeBlank(title, "title");
		this.cannotBeInThePast(dueDate);
		this.cannotBeNull(userId, "userId");

		this.title = title;
		this.dueDate = dueDate;
		this.description = description;
		this.userId = userId;
		this.status = StatusTask.PENDING;
	}
	
	public void updateTask(
		String title, 
		String description, 
		LocalDate dueDatee, 
		StatusTask status
	) {
		if (title != null && !title.isBlank())
			this.title = title;
		
		if (description != null && !description.isBlank())
			this.description = description;
		
		if (status != null)
			this.status = status;
		
		if (dueDatee != null) {
			this.cannotBeInThePast(dueDatee);
			this.dueDate = dueDatee;
		}
	}
	public void removeDueDate() {
		this.dueDate = null;
	}
	public void removeDescription() {
		this.description = "";
	}
	
	private void cannotBeInThePast(LocalDate dueDate) {
		if (dueDate != null && dueDate.isBefore(LocalDate.now()))
			throw new IllegalArgumentException("Cannot be in the past: " + "dueDate");
	}
	private void cannotBeNull(Object field, String fieldName) {
		if (field == null)
			throw new IllegalArgumentException("Cannot be null: " + fieldName);
	}
	private void cannotBeBlank(String field, String fieldName) {
		this.cannotBeNull(field, fieldName);
		if (field.isBlank())
			throw new IllegalArgumentException("Cannot be blank: " + fieldName);
	}

	public String getId() {
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

	public String getUserId() {
		return userId;
	}

    @Override
    public String toString() {
        return "Task[" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", status=" + status +
                ", userId=" + userId +
                ']';
    }
}