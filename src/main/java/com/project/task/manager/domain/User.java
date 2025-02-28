package com.project.task.manager.domain;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.project.task.manager.domain.status.Role;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity (name = "user")
@Data
@Builder
@Table (name = "user_table")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode (of = "id")
public class User implements UserDetails{
	@Id 
	@Column(
			name = "id",
			updatable = false,
			unique = true)
	@SequenceGenerator(
			name= "user_id_sequence",
			sequenceName = "user_id_sequence",
			allocationSize = 1)
	@GeneratedValue(
			strategy = GenerationType.IDENTITY,
			generator = "user_id_sequence")
	private Long id;
	
	
	@Column(name = "name", nullable = false)
	private String name;
	
	
	@Column(name = "surnaname", nullable = false)
	private String surnaname;
	
	
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	
	
	@Column(name = "password", nullable = false)
	private String password;
	
	

	@CollectionTable(
			name = "user_role",
			joinColumns = @JoinColumn(
					name = "user_id")
			)
	@Enumerated(EnumType.STRING)
	private Role role;


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
    	return true;
    }


}
