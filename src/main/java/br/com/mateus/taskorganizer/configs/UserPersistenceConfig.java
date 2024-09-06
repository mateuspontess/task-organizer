package br.com.mateus.taskorganizer.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.mateus.taskorganizer.application.gateways.UserRepository;
import br.com.mateus.taskorganizer.application.usecases.user.ExistsByLogin;
import br.com.mateus.taskorganizer.application.usecases.user.ReadUserByLogin;
import br.com.mateus.taskorganizer.application.usecases.user.SaveUser;
import br.com.mateus.taskorganizer.infra.gateways.user.UserEntityMapper;
import br.com.mateus.taskorganizer.infra.gateways.user.UserRepositoryImplMongoDB;
import br.com.mateus.taskorganizer.infra.persistence.user.UserRepositoryMongoDB;

@Configuration
public class UserPersistenceConfig {

    @Bean
    UserEntityMapper getUserEntityMapper() {
        return new UserEntityMapper();
    }

    @Bean
    UserRepository getRepository(UserRepositoryMongoDB mongoRepository, UserEntityMapper entityMapper) {
        return new UserRepositoryImplMongoDB(mongoRepository, entityMapper);
    }

    @Bean
    SaveUser saveUser(UserRepository repository) {
        return new SaveUser(repository);
    }

    @Bean
    ReadUserByLogin readUserByUsername(UserRepository repository) {
        return new ReadUserByLogin(repository);
    }

    @Bean
    ExistsByLogin existsByLogin(UserRepository repository) {
        return new ExistsByLogin(repository);
    }
}