package br.edu.atitus.remediario.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.common.aliasing.qual.Unique;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.edu.atitus.remediario.enums.UserRole;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
@Data
@Entity
@Table(name = "TB_USERS")
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity implements UserDetails {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Unique
    private String email;
    private String password;
    private UserRole role;

    public UserEntity(String email, String encryptedPassword, UserRole role) {
        this.email = email;
        this.setPassword(encryptedPassword);
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN) return List.of(
                new SimpleGrantedAuthority(UserRole.ADMIN.name()),
                new SimpleGrantedAuthority(UserRole.USER.name())
        );

        return List.of(new SimpleGrantedAuthority(UserRole.USER.name()));
    }

    @Override
    public String getUsername() {
        return email;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}