package com.project.task.manager.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response DTO with comment information")
public class CommentResponse {

    @Schema(description = "Comment ID", example = "10")
    private Long id;

    @Schema(description = "Comment author ID", example = "1")
    private Long commenterId;

    @Schema(description = "Task ID", example = "5")
    private Long taskId;

    @Schema(description = "Comment text", example = "Great idea!")
    private String text;

    @Schema(description = "Creation date", example = "2026-04-17T13:25:00")
    private LocalDateTime createdAt;

    @Schema(description = "Last update date", example = "2026-04-17T13:25:00")
    private LocalDateTime updatedAt;
}