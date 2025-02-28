package com.project.task.manager.service.implementation;

import org.springframework.stereotype.Service;

import com.project.task.manager.constants.ErrorMessage;
import com.project.task.manager.domain.User;
import com.project.task.manager.repository.UserRepository;
import com.project.task.manager.service.UserService;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

	private final UserRepository repo;
	
	@Override
	public User findById(Long id) {
		return repo.findById(id).orElseThrow(
				()-> new EntityNotFoundException(ErrorMessage.USER_NOT_FOUND));
	}

	@Override
	public User findByEmail(String email) {
		return repo.findByEmail(email).orElseThrow(
				()-> new EntityNotFoundException(ErrorMessage.USER_NOT_FOUND));
	}

}
