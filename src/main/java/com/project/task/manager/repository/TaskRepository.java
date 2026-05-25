package com.project.task.manager.repository;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.task.manager.domain.entities.Task;
import com.project.task.manager.domain.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Page <Task> findByAuthor (Pageable pageable, User Author);
	
	Page <Task> findByAgent (Pageable pageable, User Agent);

	Optional <Task> findByTitle (String title);
	
	Optional <Task> findByAuthorAndTitle(User author, String taskTitle);

    void deleteById (Long id);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Task t WHERE t.id = :id AND t.author.id = :authorId")
    boolean existsByIdAndAuthorId(@Param("id") Long id, @Param("authorId") Long authorId);
}
