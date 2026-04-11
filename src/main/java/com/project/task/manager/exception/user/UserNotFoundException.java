package com.project.task.manager.exception.user;

import lombok.Getter;

@Getter
public class UserNotFoundException extends  UserException{
    private final Long id;
    private final String email;

    public UserNotFoundException(Long id) {
        super (UserErrorCode.USER_NOT_FOUND, "user not found with id: " + id, null);
        this.id = id;
        this.email = null;
    }

    public UserNotFoundException(String email) {
        super (UserErrorCode.USER_NOT_FOUND, "user not found with email: " + email, null);
        this.id = null;
        this.email = email;
    }


}
