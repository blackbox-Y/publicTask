package com.project.task.manager.service.map;

import com.project.task.manager.domain.entities.User;
import com.project.task.manager.domain.request.UserRequest;
import com.project.task.manager.domain.response.UserResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-17T17:31:42+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserRequest request) {
        if ( request == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.name( request.getName() );
        user.surname( request.getSurname() );
        user.email( request.getEmail() );
        user.role( request.getRole() );

        return user.build();
    }

    @Override
    public UserResponse toResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.id( user.getId() );
        userResponse.name( user.getName() );
        userResponse.surname( user.getSurname() );
        userResponse.email( user.getEmail() );
        userResponse.role( user.getRole() );

        return userResponse.build();
    }

    @Override
    public User updateEntityFromRequest(UserRequest request, User user) {
        if ( request == null ) {
            return user;
        }

        if ( request.getName() != null ) {
            user.setName( request.getName() );
        }
        if ( request.getSurname() != null ) {
            user.setSurname( request.getSurname() );
        }
        if ( request.getEmail() != null ) {
            user.setEmail( request.getEmail() );
        }
        if ( request.getRole() != null ) {
            user.setRole( request.getRole() );
        }

        return user;
    }
}
