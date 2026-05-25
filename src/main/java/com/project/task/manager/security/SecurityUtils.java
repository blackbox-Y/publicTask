package com.project.task.manager.security;

import com.project.task.manager.domain.entities.User;
import com.project.task.manager.domain.status.Role;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static User getCurrentUser() {
        return (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }

    public static Long getCurrentUserId() {
        return getCurrentUser().getId();
    }

    public static String getCurrentUserEmail() {
        return getCurrentUser().getEmail();
    }

    public static boolean isCurrentUserAdmin() {
        return getCurrentUser().getRole() == Role.ADMIN;
    }

    public static boolean isOwner(Long entityOwnerId) {
        return getCurrentUserId().equals(entityOwnerId);
    }

}
