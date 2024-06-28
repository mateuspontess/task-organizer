package br.com.mateus.taskorganizer.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.mateus.taskorganizer.application.usecases.user.ReadUserByLogin;
import br.com.mateus.taskorganizer.infra.gateways.user.UserEntityMapper;

@Service
public class AuthenticationService implements UserDetailsService{
	
	@Autowired
	private UserEntityMapper mapper;
	@Autowired
	private ReadUserByLogin readUserByUsername;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return mapper.toEntity(readUserByUsername.getUserByUsername(username)); 
	}
}