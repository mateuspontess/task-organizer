package br.com.mateus.taskorganizer.controller;

import java.nio.file.AccessDeniedException;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mateus.taskorganizer.infra.security.TokenService;
import br.com.mateus.taskorganizer.model.user.User;
import br.com.mateus.taskorganizer.model.user.UserRepository;
import br.com.mateus.taskorganizer.model.user.UserRole;
import br.com.mateus.taskorganizer.model.user.authentication.DataAuthenticationDTO;
import br.com.mateus.taskorganizer.model.user.authentication.LoginResponseDTO;
import br.com.mateus.taskorganizer.model.user.authentication.RegisterDTO;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserRepository repository;
	@Autowired
	private TokenService tokenService;
	
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody @Valid DataAuthenticationDTO data) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
		var auth = this.authenticationManager.authenticate(usernamePassword);
		
		var token = tokenService.generateToken((User) auth.getPrincipal());
		
		return ResponseEntity.ok(new LoginResponseDTO(token));
	}
	
	@PostMapping("/register")
	public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
		if(this.repository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();
		
		String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
		User newUser = new User(data.login(), encryptedPassword, UserRole.USER);
		
		repository.save(newUser);
		
		return ResponseEntity.ok().build();
	}
	
    @PostMapping("/admin/register")
    public ResponseEntity adminRegister(@RequestBody @Valid RegisterDTO data) throws AccessDeniedException{
    	if(this.isAdmin()) {
        	if(this.repository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();
        	
        	String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        	User newUser = new User(data.login(), encryptedPassword, UserRole.ADMIN);
        	
        	this.repository.save(newUser);
        	
        	return ResponseEntity.ok().build();
    	}
    	throw new AccessDeniedException("Access denied");
    }
    
	private boolean isAdmin() {
		return SecurityContextHolder.getContext()
				.getAuthentication()
				.getAuthorities()
				.contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}
}
