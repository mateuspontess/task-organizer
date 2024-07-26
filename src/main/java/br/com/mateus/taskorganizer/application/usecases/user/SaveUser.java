package br.com.mateus.taskorganizer.application.usecases.user;

import br.com.mateus.taskorganizer.application.gateways.UserRepository;
import br.com.mateus.taskorganizer.domain.user.User;

public class SaveUser {

    private final UserRepository repository;

    public SaveUser(UserRepository repository) {
        this.repository = repository;
    }

    public User registerUser(User user) {            
        return this.repository.registerUser(user);
    }
}