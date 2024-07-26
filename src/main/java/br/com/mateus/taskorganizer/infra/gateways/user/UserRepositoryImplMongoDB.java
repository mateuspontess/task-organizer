package br.com.mateus.taskorganizer.infra.gateways.user;

import br.com.mateus.taskorganizer.application.gateways.UserRepository;
import br.com.mateus.taskorganizer.domain.user.User;
import br.com.mateus.taskorganizer.infra.persistence.user.UserEntity;
import br.com.mateus.taskorganizer.infra.persistence.user.UserRepositoryMongoDB;

public class UserRepositoryImplMongoDB implements UserRepository {

    private final UserRepositoryMongoDB repositoryMongoDB;
    private final UserEntityMapper mapper;

    public UserRepositoryImplMongoDB(
        UserRepositoryMongoDB mongoRepository, 
        UserEntityMapper mapper
    ) {
        this.repositoryMongoDB = mongoRepository;
        this.mapper = mapper;
    }

    
    @Override
    public User registerUser(User user) {
        UserEntity entity = mapper.toEntity(user);
        repositoryMongoDB.save(entity);

        return mapper.toUser(entity);
    }

    @Override
    public User getUserByLogin(String username) {
        UserEntity entity = repositoryMongoDB.findByLogin(username);
        
        return mapper.toUser(entity);
    }


    @Override
    public boolean existsByLogin(String login) {
        return this.repositoryMongoDB.existsByLogin(login);
    }
}