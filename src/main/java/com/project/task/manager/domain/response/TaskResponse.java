package com.project.task.manager.domain.response;

import com.project.task.manager.domain.status.PRIORITY;
import com.project.task.manager.domain.status.STATUS;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response DTO with task information")
public class TaskResponse {
    @Schema(description = "Task ID", example = "1")
    private Long id;

    @Schema(description = "Author ID", example = "1")
    private Long authorId;

    @Schema(description = "Agent ID", example = "2")
    private Long agentId;

    @Schema(description = "Task title", example = "Develop API")
    private String title;

    @Schema(description = "Task description", example = "Detailed task description...")
    private String description;

    @Schema(description = "Execution steps")
    private List<String> steps;

    @Schema(description = "Task priority", allowableValues = {"LOW", "MEDIUM", "HIGH", "CRITICAL"})
    private PRIORITY priority;

    @Schema(description = "Task status", allowableValues = {"NEW", "IN_PROGRESS", "DONE", "CANCELLED"})
    private STATUS status;

    @Schema(description = "Comment IDs")
    private List<Long> commentIds;

    @Schema(description = "Creation date")
    private LocalDateTime createdAt;

    @Schema(description = "Update date")
    private LocalDateTime updatedAt;
}