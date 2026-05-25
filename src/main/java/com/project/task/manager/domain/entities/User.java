package com.project.task.manager.domain.entities;

import com.project.task.manager.domain.status.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@Table (name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails{
	@Id 
	@Column(
			name = "id",
			updatable = false,
			unique = true,
            nullable = false
    )
	@SequenceGenerator(
			name= "user_id_sequence",
			sequenceName = "user_id_sequence",
			allocationSize = 1
    )
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "user_id_sequence")
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "surname", nullable = false)
	private String surname;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

    @Builder.Default
    @Column(name = "enabled", nullable = false)
    private boolean enabled = false;

    @Builder.Default
	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false)
	private Role role = Role.USER;


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}


	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public String getPassword() {
		return password;
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
        return enabled;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        Class<?> oEffectiveClass = o instanceof HibernateProxy proxy
                ? proxy.getHibernateLazyInitializer().getPersistentClass()
                : o.getClass();

        Class<?> thisEffectiveClass = this instanceof HibernateProxy proxy
                ? proxy.getHibernateLazyInitializer().getPersistentClass()
                : this.getClass();

        if (thisEffectiveClass != oEffectiveClass) return false;

        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }

    public String getFullName() {return name + " " + surname;}
    @Override
    public String toString () {
        return "User{" +
                "id= " + id +
                ", name= " + name +
                ", surname= " + surname +
                ", email= " + email +
                ", role= " + role.toString() +
                "}";
    }
}
