package com.project.task.manager.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.task.manager.domain.Comments;
import com.project.task.manager.domain.Task;

public interface CommentRepository extends JpaRepository <Comments, Long>{
	
	Page <Comments> findByTask (Task task, Pageable pageable);
	
}
