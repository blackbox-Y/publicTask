package com.project.task.manager.domain.exception.application;

//4xx exception
public abstract class ClientException extends ApplicationException {

    protected ClientException(
            int httpStatus,
            String errorCode,
            String message,
            Throwable cause
    ) {
        super(
                httpStatus,
                message,
                errorCode,
                cause
        );
    }

    protected ClientException(
            int httpStatus,
            String errorCode,
            String message
    ) {
        super(
                httpStatus,
                message,
                errorCode
        );
    }
}
