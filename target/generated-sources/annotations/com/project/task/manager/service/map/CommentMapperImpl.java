package com.project.task.manager.service.map;

import com.project.task.manager.domain.entities.Comment;
import com.project.task.manager.domain.entities.Task;
import com.project.task.manager.domain.entities.User;
import com.project.task.manager.domain.request.CommentRequest;
import com.project.task.manager.domain.response.CommentResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-17T17:31:42+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public Comment toEntity(CommentRequest request) {
        if ( request == null ) {
            return null;
        }

        Comment.CommentBuilder comment = Comment.builder();

        comment.text( request.getText() );

        return comment.build();
    }

    @Override
    public CommentResponse toResponse(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentResponse.CommentResponseBuilder commentResponse = CommentResponse.builder();

        commentResponse.commenterId( commentCommenterId( comment ) );
        commentResponse.taskId( commentTaskId( comment ) );
        commentResponse.id( comment.getId() );
        commentResponse.text( comment.getText() );
        commentResponse.createdAt( comment.getCreatedAt() );
        commentResponse.updatedAt( comment.getUpdatedAt() );

        return commentResponse.build();
    }

    @Override
    public Comment updateEntityFromRequest(CommentRequest request, Comment comment) {
        if ( request == null ) {
            return comment;
        }

        if ( request.getText() != null ) {
            comment.setText( request.getText() );
        }

        return comment;
    }

    private Long commentCommenterId(Comment comment) {
        User commenter = comment.getCommenter();
        if ( commenter == null ) {
            return null;
        }
        return commenter.getId();
    }

    private Long commentTaskId(Comment comment) {
        Task task = comment.getTask();
        if ( task == null ) {
            return null;
        }
        return task.getId();
    }
}
