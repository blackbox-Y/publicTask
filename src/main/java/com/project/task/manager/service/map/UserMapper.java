package com.project.task.manager.service.map;


import com.project.task.manager.domain.entities.User;
import com.project.task.manager.domain.request.UserRequest;
import com.project.task.manager.domain.response.UserResponse;
import com.project.task.manager.domain.status.Role;

import org.mapstruct.*;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    User toEntity(UserRequest request);

    UserResponse toResponse(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "password", ignore = true)
    User updateEntityFromRequest(UserRequest request, @MappingTarget User user);


    default Page <UserResponse> toResponsePage (Page <User> userPage) {
        return userPage.map(this::toResponse);
    }
}