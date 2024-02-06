package br.com.mateus.taskorganizer.model.user;

public record RegisterDTO(
		String login,
		String password,
		UserRole role) {

}
