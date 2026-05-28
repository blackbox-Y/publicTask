package com.project.task.manager.domain.exception.authemtication;

import com.project.task.manager.domain.exception.application.ApplicationException;
import com.project.task.manager.domain.exception.application.BusinessException;
import com.project.task.manager.domain.response.AuthenticationResponse;

public abstract class AuthenticationException extends ApplicationException {

    protected AuthenticationException(
            int httpStatus,
            String errorCode,
            String message
    ) {
        super(
                httpStatus,
                errorCode,
                message
        );
    }
}