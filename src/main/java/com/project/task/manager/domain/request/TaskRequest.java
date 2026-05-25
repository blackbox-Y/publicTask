package com.project.task.manager.domain.request;

import com.project.task.manager.domain.status.PRIORITY;
import com.project.task.manager.domain.status.STATUS;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO for creating/updating a task")
public class TaskRequest {
    //todo reduce number of fields in the class

    @Schema(description = "Task author ID", example = "1")
    private Long authorId;

    @Schema(description = "Task agent ID", example = "2")
    private Long agentId;

    @Schema(description = "Task title", required = true, example = "Develop API")
    private String title;

    @Schema(description = "Task description", example = "Detailed task description...")
    private String description;

    @Schema(description = "Execution steps")
    @Builder.Default
    private List<String> steps = new ArrayList<>();

    @Schema(description = "Task priority", allowableValues = {"LOW", "MEDIUM", "HIGH", "CRITICAL"})
    private PRIORITY priority;

    @Schema(description = "Task status", allowableValues = {"NEW", "IN_PROGRESS", "DONE", "CANCELLED"})
    private STATUS status;
}