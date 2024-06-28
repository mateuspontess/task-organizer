package br.com.mateus.taskorganizer.application.usecases.user;

import br.com.mateus.taskorganizer.application.gateways.UserRepository;
import br.com.mateus.taskorganizer.domain.user.User;

public class ReadUserByLogin {

    private final UserRepository repository;

    
    public ReadUserByLogin(UserRepository repository) {
        this.repository = repository;
    }

    public User getUserByUsername(String login) {
        return this.repository.getUserByLogin(login);
    }
}