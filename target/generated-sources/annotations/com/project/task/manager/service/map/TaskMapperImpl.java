package com.project.task.manager.service.map;

import com.project.task.manager.domain.entities.Task;
import com.project.task.manager.domain.request.TaskRequest;
import com.project.task.manager.domain.response.TaskResponse;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-17T17:31:42+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class TaskMapperImpl implements TaskMapper {

    @Override
    public Task toEntity(TaskRequest request) {
        if ( request == null ) {
            return null;
        }

        Task.TaskBuilder task = Task.builder();

        task.title( request.getTitle() );
        task.description( request.getDescription() );
        List<String> list = request.getSteps();
        if ( list != null ) {
            task.steps( new ArrayList<String>( list ) );
        }
        task.priority( request.getPriority() );
        task.status( request.getStatus() );

        return task.build();
    }

    @Override
    public TaskResponse toResponse(Task task) {
        if ( task == null ) {
            return null;
        }

        TaskResponse.TaskResponseBuilder taskResponse = TaskResponse.builder();

        taskResponse.id( task.getId() );
        taskResponse.title( task.getTitle() );
        taskResponse.description( task.getDescription() );
        List<String> list = task.getSteps();
        if ( list != null ) {
            taskResponse.steps( new ArrayList<String>( list ) );
        }
        taskResponse.priority( task.getPriority() );
        taskResponse.status( task.getStatus() );

        taskResponse.authorId( task.getAuthorId() );
        taskResponse.agentId( task.getAgentId() );
        taskResponse.commentIds( task.getCommentIds() );

        return taskResponse.build();
    }

    @Override
    public Task updateEntityFromRequest(TaskRequest request, Task task) {
        if ( request == null ) {
            return task;
        }

        if ( request.getTitle() != null ) {
            task.setTitle( request.getTitle() );
        }
        if ( request.getDescription() != null ) {
            task.setDescription( request.getDescription() );
        }
        if ( task.getSteps() != null ) {
            List<String> list = request.getSteps();
            if ( list != null ) {
                task.getSteps().clear();
                task.getSteps().addAll( list );
            }
        }
        else {
            List<String> list = request.getSteps();
            if ( list != null ) {
                task.setSteps( new ArrayList<String>( list ) );
            }
        }
        if ( request.getPriority() != null ) {
            task.setPriority( request.getPriority() );
        }
        if ( request.getStatus() != null ) {
            task.setStatus( request.getStatus() );
        }

        return task;
    }
}
