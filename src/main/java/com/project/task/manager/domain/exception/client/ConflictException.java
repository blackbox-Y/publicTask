package com.project.task.manager.domain.exception.entity;

import com.project.task.manager.domain.exception.application.ClientException;

public class ConflictException extends ClientException {

    protected ConflictException(
            String entityType,
            String field,
            String value
    ) {
        super(
                409,
                "CONFLICT_001",
                String.format("%s with %s '%s' already exists", entityType, field, value)
        );
    }
}
