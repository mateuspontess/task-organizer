package br.com.mateus.taskorganizer.infra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mateus.taskorganizer.application.dto.user.input.AuthenticationDTO;
import br.com.mateus.taskorganizer.application.dto.user.output.AuthenticationResponseDTO;
import br.com.mateus.taskorganizer.application.usecases.user.ExistsByLogin;
import br.com.mateus.taskorganizer.application.usecases.user.SaveUser;
import br.com.mateus.taskorganizer.domain.user.User;
import br.com.mateus.taskorganizer.domain.user.UserRole;
import br.com.mateus.taskorganizer.infra.persistence.user.UserEntity;
import br.com.mateus.taskorganizer.infra.security.TokenService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private PasswordEncoder encoder;

	// use cases
	@Autowired
	private ExistsByLogin existsByLogin;
	@Autowired
	private SaveUser saveUser;
	
	
	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
		Authentication auth = 
			this.authenticationManager.authenticate(usernamePassword);

		var token = tokenService.generateToken((UserEntity) auth.getPrincipal());
		
		return ResponseEntity.ok(new AuthenticationResponseDTO(token));
	}
	
	@PostMapping("/register")
	public ResponseEntity<Void> register(@RequestBody @Valid AuthenticationDTO data) {
		this.checkIfUserAlreadyExists(data.login());

		String encryptedPassword = encoder.encode(data.password());
		User newUser = new User(data.login(), encryptedPassword, UserRole.USER);
		
		saveUser.registerUser(newUser);
		
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/admin/register")
	public ResponseEntity<Void> adminRegister(@RequestBody @Valid AuthenticationDTO data) {
		this.checkIfUserAlreadyExists(data.login());
		
		String encryptedPassword = encoder.encode(data.password());
		User newUser = new User(data.login(), encryptedPassword, UserRole.ADMIN);
		
		saveUser.registerUser(newUser);
		
		return ResponseEntity.ok().build();
	}

	private void checkIfUserAlreadyExists(String username) {
		if(this.existsByLogin.existsByLogin(username)) 
			throw new IllegalArgumentException("Login unavailable");
	}
}