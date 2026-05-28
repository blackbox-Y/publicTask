package com.project.task.manager.domain.exception.client;

import lombok.Getter;

@Getter
public class NotFoundException extends ClientException {

    public NotFoundException(String entityType, Long id) {
        super(
                404,
                "NOT_FOUND_001",
                String.format("%s with id %d not found", entityType, id)
        );
    }

    public NotFoundException(String entityType, String identifier) {
        super(
                404,
                "NOT_FOUND_002",
                String.format("%s with %s not found", entityType, identifier)
        );
    }
}