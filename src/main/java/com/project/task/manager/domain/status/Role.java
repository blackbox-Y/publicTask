package com.project.task.manager.domain.status;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
	ADMIN,
	USER, 
	GUEST;

	@Override
	public String getAuthority() {
		return name();
	}
}
