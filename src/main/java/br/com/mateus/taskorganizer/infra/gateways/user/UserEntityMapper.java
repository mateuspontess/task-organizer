package br.com.mateus.taskorganizer.infra.gateways.user;

import br.com.mateus.taskorganizer.domain.user.User;
import br.com.mateus.taskorganizer.infra.persistence.user.UserEntity;

public class UserEntityMapper {

    public UserEntity toEntity(User user) {
        return new UserEntity(
            user.getId(),
            user.getLogin(),
            user.getPassword(),
            user.getRole(),
            user.getTaskIds()
        );
    }

    public User toUser(UserEntity entity) {
        return new User(
            entity.getId(),
            entity.getLogin(),
            entity.getPassword(),
            entity.getRole(),
            entity.getTaskIds()
        );
    }
}