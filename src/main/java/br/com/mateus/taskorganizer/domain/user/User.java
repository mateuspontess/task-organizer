package br.com.mateus.taskorganizer.domain.user;

import java.util.ArrayList;
import java.util.List;


public class User {
	
	private Long id;
	private String login;
	private String password;
	private UserRole role;
	private List<Long> taskIds = new ArrayList<>();
	
	
	public User(String login, String password, UserRole role) {
		this.login = login;
		this.password = password;
		this.role = role;
	}

    public User(Long id, String login, String password, UserRole role, List<Long> taskIds) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
        this.taskIds = taskIds;
    }

	public UserRole getRole() {
		return this.role;
	}

	public Long getId() {
		return id;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public List<Long> getTaskIds() {
		return taskIds;
	}
}