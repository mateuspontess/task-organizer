package br.com.mateus.taskorganizer.domain.task;

public enum StatusTask {
	CONCLUDED("concluded"),
	PENDING("pending");
	
	private String status;
	
	
	StatusTask(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status.toUpperCase();
	}
	
	public static StatusTask fromString(String value) {
		for (StatusTask status : StatusTask.values()) {
			if (status.status.equalsIgnoreCase(value)) 
				return status;
		}
		throw new IllegalArgumentException("Invalid status value: " + value);
	}
}