package br.com.mateus.taskorganizer.application.usecases.user;

import br.com.mateus.taskorganizer.application.gateways.UserRepository;

public class ExistsByLogin {

    private final UserRepository repository;

    
    public ExistsByLogin(UserRepository repository) {
        this.repository = repository;
    }

    public boolean existsByLogin(String login) {
        return this.repository.existsByLogin(login);
    }
}