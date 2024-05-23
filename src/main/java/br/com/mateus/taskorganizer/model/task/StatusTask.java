package br.com.mateus.taskorganizer.model.task;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusTask {
	CONCLUDED("concluded"),
	PENDING("pending");
	
	private String status;
	
	
	StatusTask(String status) {
		this.status = status;
	}
	
	@JsonValue
	public String getStatus() {
		return this.status.toUpperCase();
	}
	
	@JsonCreator
	public static StatusTask fromString(String value) {
		for (StatusTask status : StatusTask.values()) {
			if (status.status.equalsIgnoreCase(value)) 
				return status;
		}
		throw new IllegalArgumentException("Invalid status value: " + value);
	}
}