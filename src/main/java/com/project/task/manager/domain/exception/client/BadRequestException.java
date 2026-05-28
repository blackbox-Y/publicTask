package com.project.task.manager.domain.exception.entity;

import com.project.task.manager.domain.exception.application.ClientException;

public class BadRequestException extends ClientException {

    public BadRequestException(String message) {
        super(
                400,
                "BAD_REQUEST_001",
                message
        );
    }

    public BadRequestException (String errorCode, String message) {
        super(400, errorCode, message);
    }
}
