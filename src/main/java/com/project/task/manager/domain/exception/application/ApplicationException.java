package com.project.task.manager.domain.exception.application;

import lombok.Getter;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

@Getter
public abstract class ApplicationException extends RuntimeException {

    private final String errorCode;
    private final int httpStatus;
    private final Instant timestamp;

    protected ApplicationException(
            int httpStatus,
            String errorCode,
            String message,
            Throwable cause
    ) {
        super(message, cause);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.timestamp = Instant.now();
    }

    protected ApplicationException(
            int httpStatus,
            String errorCode,
            String message
    ) {
        super(message, null);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.timestamp = Instant.now();
    }

}