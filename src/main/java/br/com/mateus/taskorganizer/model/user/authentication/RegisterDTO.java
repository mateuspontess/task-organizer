package br.com.mateus.taskorganizer.model.user.authentication;

import br.com.mateus.taskorganizer.model.user.UserRole;

public record RegisterDTO(
		String login,
		String password,
		UserRole role) {

}
