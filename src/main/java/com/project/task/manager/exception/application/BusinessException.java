package com.project.task.manager.exception.application;

public abstract class BusinessException extends ApplicationException{
    protected BusinessException(String errorCode, String message, Throwable cause) {
        super(errorCode, message);
    }
}
