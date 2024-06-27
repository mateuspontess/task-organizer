package br.com.mateus.taskorganizer.application.gateways;

import br.com.mateus.taskorganizer.domain.user.User;

public interface UserRepository {

    public User registerUser(User user);
    public User getUserByLogin(String login);
    public boolean existsByLogin(String login);
}