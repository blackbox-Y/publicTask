package com.project.task.manager.service;

import com.project.task.manager.domain.User;

public interface UserService {
	
	User findById (Long id);
	
	User findByEmail (String email);
}
