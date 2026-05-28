package com.project.task.manager.security;

import com.project.task.manager.domain.exception.client.EntityException;
import com.project.task.manager.domain.exception.client.EntityNotFoundException;
import com.project.task.manager.repository.CommentRepository;
import com.project.task.manager.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.project.task.manager.repository.TaskRepository;

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