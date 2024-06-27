package br.com.mateus.taskorganizer.application.usecases.user;

import br.com.mateus.taskorganizer.application.gateways.UserRepository;
import br.com.mateus.taskorganizer.domain.user.User;

public class ReadUserByUsername {

    private final UserRepository repository;

    
    public ReadUserByUsername(UserRepository repository) {
        this.repository = repository;
    }

    public User getUserByUsername(String username) {
        return this.repository.getUserByLogin(username);
    }
}