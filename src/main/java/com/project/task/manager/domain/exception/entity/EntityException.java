package com.project.task.manager.domain.exception.entity;

import com.project.task.manager.domain.exception.application.BusinessException;

public class EntityException extends BusinessException {
    private final EntityErrorCode errorCode;
    private final EntityType entityType;

    public enum EntityErrorCode {
        NOT_FOUND("ENTITY_001"),
        ALREADY_EXISTS("ENTITY_002"),
        INVALID_DATA("ENTITY_003"),
        DUPLICATE_KEY("ENTITY_004"),
        FOREIGN_KEY_VIOLATION("ENTITY_005");

        private final String code;
        EntityErrorCode(String code) {this.code = code;}
        public String getCode() {return code;}
    }

    public enum EntityType {
        USER("User"),
        TASK("Task"),
        COMMENT("Comment");

        private final String type;
        EntityType(String type) {this.type = type;}
        public  String getType() {return  type;}
    }

    public EntityException(
            EntityErrorCode errorCode,
            String message,
            EntityType entityType,
            Throwable cause
    ) {super(
            errorCode.getCode(),
            String.format("[%s] %s", entityType, message),
            cause);

        this.errorCode = errorCode;
        this.entityType = entityType;
    }
}