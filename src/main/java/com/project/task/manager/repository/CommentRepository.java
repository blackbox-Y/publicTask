package com.project.task.manager.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.task.manager.domain.entities.Comment;
import com.project.task.manager.domain.entities.Task;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository <Comment, Long>{

	Page <Comment> findByTask (Task task, Pageable pageable);


    @Modifying
    @Query("DELETE FROM Comment c WHERE c.task = :task")
    void deleteByTask(@Param("task") Task task);

    List<Comment> findByTask(Task task);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Comment c WHERE c.id = :id AND c.commenter.id = :commenterId")
    boolean existsByIdAndCommenterId(@Param("id") Long id, @Param("commenterId") Long commenterId);
}
