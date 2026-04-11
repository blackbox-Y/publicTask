package com.project.task.manager.exception.application;

public abstract class TechnicalException extends ApplicationException {
    protected TechnicalException(String errorCode, String message, Throwable cause) {
        super(errorCode, message);
    }
}
