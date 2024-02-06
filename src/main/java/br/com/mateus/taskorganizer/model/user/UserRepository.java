package br.com.mateus.taskorganizer.model.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByLogin(String login); 
}
