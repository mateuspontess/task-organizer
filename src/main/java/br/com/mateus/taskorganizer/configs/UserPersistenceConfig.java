package br.com.mateus.taskorganizer.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.mateus.taskorganizer.application.gateways.UserRepository;
import br.com.mateus.taskorganizer.application.usecases.user.SaveUser;
import br.com.mateus.taskorganizer.infra.gateways.user.UserEntityMapper;
import br.com.mateus.taskorganizer.infra.gateways.user.UserRepositoryImplJPA;
import br.com.mateus.taskorganizer.infra.persistence.user.UserRepositoryJPA;
import br.com.mateus.taskorganizer.application.usecases.user.ExistsByLogin;
import br.com.mateus.taskorganizer.application.usecases.user.ReadUserByUsername;

@Configuration
public class UserPersistenceConfig {

    @Bean
    UserEntityMapper getUserEntityMapper() {
        return new UserEntityMapper();
    }

    @Bean
    UserRepository getRepository(UserRepositoryJPA repository, UserEntityMapper entityMapper) {
        return new UserRepositoryImplJPA(repository, entityMapper);
    }

    @Bean
    SaveUser saveUser(UserRepository repository) {
        return new SaveUser(repository);
    }

    @Bean
    ReadUserByUsername readUserByUsername(UserRepository repository) {
        return new ReadUserByUsername(repository);
    }

    @Bean
    ExistsByLogin existsByLogin(UserRepository repository) {
        return new ExistsByLogin(repository);
    }
}