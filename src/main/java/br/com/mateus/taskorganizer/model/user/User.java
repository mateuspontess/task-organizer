package br.com.mateus.taskorganizer.model.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.mateus.taskorganizer.model.task.Task;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Getter
	private Long id;
	
	private String login;
	private String password;
	@Enumerated(EnumType.STRING) @Getter
	private UserRole role;
	
	@OneToMany(mappedBy = "user")
	private List<Task> tasks = new ArrayList<>();
	
	public User(String login, String password, UserRole role) {
		this.login = login;
		this.password = password;
		this.role = role;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return (this.role == UserRole.ADMIN) ?
				List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"))
				: List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}
	@Override
	public String getPassword() {
		return this.password;
	}
	@Override
	public String getUsername() {
		return this.login;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
}