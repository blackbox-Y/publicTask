package com.project.task.manager.exception.user;


import com.project.task.manager.exception.application.BusinessException;

public class UserException extends BusinessException {
    private final UserErrorCode userErrorCode;

    public enum UserErrorCode {
        USER_NOT_FOUND("USER_001"),
        USER_ALREADY_EXISTS("USER_002"),
        INVALID_USER_DATA("USER_003"),
        USER_BLOCKED("USER_004");

        private final String code;

        UserErrorCode(String code) {
            this.code = code;
        }
    }

    public UserException (UserErrorCode errorCode, String message, Throwable cause) {
        super(errorCode.code, message, cause);
        this.userErrorCode = errorCode;
    }

}