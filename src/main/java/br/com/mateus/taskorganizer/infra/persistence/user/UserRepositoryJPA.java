package br.com.mateus.taskorganizer.infra.persistence.user;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepositoryJPA extends JpaRepository<UserEntity, Long>{

	UserEntity findByLogin(String login);

	boolean existsByLogin(String login);
}