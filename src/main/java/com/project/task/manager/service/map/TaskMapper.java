package com.project.task.manager.service.map;

import java.util.List;

import com.project.task.manager.domain.entities.Comment;
import com.project.task.manager.domain.response.TaskResponse;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

import com.project.task.manager.domain.entities.Task;
import com.project.task.manager.domain.request.TaskRequest;


@Mapper (componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface TaskMapper {

//    @Mapping(target = "authorId", ignore = true)
//    @Mapping(target = "agentId", ignore = true)
//    @Mapping(target = "comments", ignore = true)
//    @Mapping(target = "steps", source = "steps")
//    @Mapping(target = "status", defaultValue = "WAITS")
    Task toEntity(TaskRequest request);

    @Mapping(target = "authorId", expression = "java(task.getAuthorId())")
    @Mapping(target = "agentId", expression = "java(task.getAgentId())")
    @Mapping(target = "commentIds", expression = "java(task.getCommentIds())")
    TaskResponse toResponse(Task task);

    @Named("commentIds")
    default List<Long> commentIds(List<Comment> comments) {
        //todo нужна логика в comment service по которой будут искаться энтити по этому списку id
        return comments != null
                ? comments.stream()
                .map(Comment::getId).toList()
                : List.of();
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Task updateEntityFromRequest(TaskRequest request, @MappingTarget Task task);

    default Page<TaskResponse> toResponsePage(Page<Task> taskPage) {
        return taskPage.map(this::toResponse);
    }


}
