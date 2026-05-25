package com.project.task.manager.security;

import com.project.task.manager.domain.entities.Comment;
import com.project.task.manager.domain.exception.entity.EntityException;
import com.project.task.manager.domain.exception.entity.EntityNotFoundException;
import com.project.task.manager.repository.CommentRepository;
import com.project.task.manager.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.project.task.manager.domain.exception.TaskNotFoundException;
import com.project.task.manager.domain.entities.Task;
import com.project.task.manager.repository.TaskRepository;

import java.util.Objects;

@Service
@AllArgsConstructor
public class SecurityService {

    private TaskRepository taskRepository;
    private CommentRepository commentRepository;
    private UserRepository userRepository;



    public boolean canAccessTask(Long id) {
        if (SecurityUtils.isCurrentUserAdmin()) return true;

        Long currentUserId = SecurityUtils.getCurrentUserId();

        return taskRepository.existsByIdAndAuthorId(id, currentUserId);
    }

    public boolean canAccessComment(Long id) {
        if (SecurityUtils.isCurrentUserAdmin()) return true;

        Long currentUserId = SecurityUtils.getCurrentUserId();
        return commentRepository.existsByIdAndCommenterId(id, currentUserId);
    }

    public boolean canAccessUser(Long userId) {
        if (SecurityUtils.isCurrentUserAdmin()) return true;

        Long currentUserId = SecurityUtils.getCurrentUserId();
        return currentUserId.equals(userId);
    }





    public boolean canCreate () {return SecurityUtils.getCurrentUser() != null;} //todo разобраться с тем что он дублирует isAuthenticated() research
}