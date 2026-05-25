package com.project.task.manager.domain.exception.entity;

import com.project.task.manager.domain.exception.application.BusinessException;
import lombok.Getter;

@Getter
public class EntityNotFoundException extends EntityException {

    private final Long id;
    private final String identifier;

    public EntityNotFoundException(EntityType entityType, Long id, String message, Throwable cause) {
        super(EntityErrorCode.NOT_FOUND,
                String.format("%s not found by ID: %d. %s", entityType.getType(), id, message), entityType, cause);

        this.id = id;
        this.identifier = null;
    }

    // Универсальный: любой entity по identifier (email/title и т.д.)
    public EntityNotFoundException(EntityType entityType, String identifier, String message, Throwable cause) {
        super(EntityErrorCode.NOT_FOUND,
                String.format("%s not found by %s: '%s'. %s", entityType.getType(), identifier, identifier, message), entityType, cause);

        this.id = null;
        this.identifier = identifier;
    }

    // Короткие: без message/cause (default)
    public EntityNotFoundException(EntityType entityType, Long id) {
        this(
                entityType,
                id,
                entityType.getType() + " not found",
                null);
    }

    public EntityNotFoundException(EntityType entityType, String identifier) {
        this(
                entityType,
                identifier,
                entityType.getType() +" not found",
                null);
    }
}