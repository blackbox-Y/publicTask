package com.project.task.manager.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO for creating/updating a comment")
public class CommentRequest {

    @Schema(description = "User ID of the commenter", example = "1")
    private Long commenterId;

    @Schema(description = "Task ID", required = true, example = "5")
    private Long taskId;

    @Schema(description = "Comment text", required = true, example = "Great idea!")
    private String text;
}