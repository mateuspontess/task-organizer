package br.com.mateus.taskorganizer.infra.persistence.user;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepositoryMongoDB extends MongoRepository<UserEntity, String>{

	UserEntity findByLogin(String login);

	boolean existsByLogin(String login);
}