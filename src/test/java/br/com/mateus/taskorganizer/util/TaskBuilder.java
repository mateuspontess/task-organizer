package br.com.mateus.taskorganizer.util;

import java.time.LocalDate;

import br.com.mateus.taskorganizer.model.task.StatusTask;
import br.com.mateus.taskorganizer.model.task.Task;
import br.com.mateus.taskorganizer.model.user.User;

public class TaskBuilder {

	private Long id;
	private String title;
	private String description;
	private LocalDate dueDate;
	private StatusTask status;
	private User user;
	
	public TaskBuilder id(Long id) {
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
	
	public Task build() {
		Task task =  new Task(this.title, this.description, this.dueDate, this.user);
		task.setId(this.id);
		return task;
	}
}
