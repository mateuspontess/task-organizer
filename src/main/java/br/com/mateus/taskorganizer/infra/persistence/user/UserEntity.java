package br.com.mateus.taskorganizer.infra.persistence.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.mateus.taskorganizer.domain.user.User;
import br.com.mateus.taskorganizer.domain.user.UserRole;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity implements UserDetails {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String login;

	@Column(nullable = false)
	private String password;
	
	@Enumerated(EnumType.STRING) 
	@Column(nullable = false)
	private UserRole role;
	
	@ElementCollection
    @CollectionTable(name = "user_tasks", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "task_id", nullable = true)
	private List<Long> taskIds = new ArrayList<>();


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
		return (this.role == UserRole.ADMIN) ? List.of(UserRole.ADMIN, UserRole.USER) : List.of(UserRole.USER);
	}
	private List<String> getRolesAsString() {
		return (this.role == UserRole.ADMIN) ? List.of("ROLE_ADMIN", "ROLE_USER") : List.of("ROLE_USER");
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.getRolesAsString().stream().map(r -> new SimpleGrantedAuthority(r)).toList();
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