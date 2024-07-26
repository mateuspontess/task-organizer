package br.com.mateus.taskorganizer.tests_utils;

import java.time.LocalDate;

import br.com.mateus.taskorganizer.domain.task.StatusTask;
import br.com.mateus.taskorganizer.domain.task.Task;

public class TaskBuilder {

	private String id;
	private String title;
	private String description;
	private LocalDate dueDate;
	private StatusTask status;
	private String userId;
	
	public TaskBuilder id(String id) {
		this.id = id;
		return this;
	}
	public TaskBuilder title(String title) {
		this.title = title;
		return this;
	}
	public TaskBuilder description(String description) {
		this.description = description;
		return this;
	}
	public TaskBuilder dueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
		return this;
	}
	public TaskBuilder status(StatusTask status) {
		this.status = status;
		return this;
	}
	public TaskBuilder userId(String userId) {
		this.userId = userId;
		return this;
	}
	
	public Task build() {
		Task task = new Task(this.id, this.title, this.description, this.dueDate, this.status, this.userId);
		return task;
	}
}
