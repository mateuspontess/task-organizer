package br.com.mateus.taskorganizer.domain.user;

import java.util.ArrayList;
import java.util.List;


public class User {
	
	private String id;
	private String login;
	private String password;
	private UserRole role;
	private List<String> taskIds = new ArrayList<>();
	
	
	public User(String login, String password, UserRole role) {
		this.login = login;
		this.password = password;
		this.role = role;
	}

    public User(
		String id, 
		String login, 
		String password, 
		UserRole role, 
		List<String> taskIds
	) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
        this.taskIds = taskIds;
    }

	public UserRole getRole() {
		return this.role;
	}

	public String getId() {
		return id;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public List<String> getTaskIds() {
		return taskIds;
	}

	@Override
	public String toString() {
		return "User{" +
				"id='" + id + '\'' +
				", login='" + login + '\'' +
				", role=" + role +
				", taskIds=" + taskIds +
				'}';
	}
}