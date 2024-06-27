package br.com.mateus.taskorganizer.infra.gateways.user;

import br.com.mateus.taskorganizer.application.gateways.UserRepository;
import br.com.mateus.taskorganizer.domain.user.User;
import br.com.mateus.taskorganizer.infra.persistence.user.UserEntity;
import br.com.mateus.taskorganizer.infra.persistence.user.UserRepositoryJPA;

public class UserRepositoryImplJPA implements UserRepository {

    private final UserRepositoryJPA repositoryJPA;
    private final UserEntityMapper mapper;

    public UserRepositoryImplJPA(UserRepositoryJPA repositoryJPA, UserEntityMapper mapper) {
        this.repositoryJPA = repositoryJPA;
        this.mapper = mapper;
    }

    
    @Override
    public User registerUser(User user) {
        UserEntity entity = mapper.toEntity(user);
        repositoryJPA.save(entity);

        return mapper.toUser(entity);
    }

    @Override
    public User getUserByLogin(String username) {
        UserEntity entity = repositoryJPA.findByLogin(username);
        
        return mapper.toUser(entity);
    }


    @Override
    public boolean existsByLogin(String login) {
        return this.repositoryJPA.existsByLogin(login);
    }
}