package com.project.task.manager.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.task.manager.domain.User;

public interface UserRepository extends JpaRepository <User, Long>{ 
	Page <User> findAll (Pageable pageable);
	
	Optional <User> findByEmail (String email);
	
	void deleteById (Long id);

}
