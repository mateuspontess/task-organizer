package br.com.mateus.taskorganizer.infra.persistence.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.mateus.taskorganizer.domain.user.User;
import br.com.mateus.taskorganizer.domain.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class UserEntity implements UserDetails {
	
	@Id
	private String id;
	@Indexed(unique = true)
	private String login;
	private String password;
	private UserRole role;
	private List<String> taskIds = new ArrayList<>();


	public UserEntity(User user) {
		this.id = user.getId();
		this.login = user.getLogin();
		this.password = user.getPassword();
		this.role = user.getRole();
		this.taskIds = user.getTaskIds();
	}


	public UserRole getRole() {
		return this.role;
	}
	public List<UserRole> getRoles() {
		return (this.role == UserRole.ADMIN) ? 
			List.of(UserRole.ADMIN, UserRole.USER) : List.of(UserRole.USER);
	}
	private List<String> getRolesAsString() {
		return (this.role == UserRole.ADMIN) ? 
			List.of("ROLE_ADMIN", "ROLE_USER") : List.of("ROLE_USER");
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.getRolesAsString().stream()
			.map(r -> new SimpleGrantedAuthority(r)).toList();
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