package com.project.task.manager.service.map;

import com.project.task.manager.domain.entities.Comment;
import com.project.task.manager.domain.request.CommentRequest;
import com.project.task.manager.domain.response.CommentResponse;
import org.mapstruct.*;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

@Mapper (componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface CommentMapper {
    @Mapping(target = "commenter", ignore = true)
    @Mapping(target = "task", ignore = true)
    Comment toEntity(CommentRequest request);

    @Mapping(source = "commenter.id", target = "commenterId")
    @Mapping(source = "task.id", target = "taskId")
    CommentResponse toResponse(Comment comment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Comment updateEntityFromRequest (CommentRequest request, @MappingTarget Comment comment);

    default Page <CommentResponse> toResponsePage (Page <Comment> comments) {
        return comments.map(this::toResponse);
    }

}

