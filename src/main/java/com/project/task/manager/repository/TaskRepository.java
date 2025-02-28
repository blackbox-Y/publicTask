package com.project.task.manager.repository;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.task.manager.domain.Task;
import com.project.task.manager.domain.User;

public interface TaskRepository extends JpaRepository<Task, Long> {
	
	Page <Task> findAll (Pageable pageable);
	
	Page <Task> findByAuthor (Pageable pageable, User Author);
	
	Page <Task> findByAgent (Pageable pageable, User Agent);
	
	ArrayList<Task> findByAuthor (User Author);
	
	Optional <Task> findByTitle (String title);
	
	Task findByAuthorAndTitle(User author, String taskTitle);
	
	void deleteById (Long id);
}
