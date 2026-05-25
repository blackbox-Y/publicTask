package com.project.task.manager.controller;

import com.project.task.manager.domain.request.CommentRequest;
import com.project.task.manager.domain.request.TaskRequest;
import com.project.task.manager.domain.response.CommentResponse;
import com.project.task.manager.domain.response.TaskResponse;
import com.project.task.manager.domain.status.STATUS;
import com.project.task.manager.service.implementation.CommentServiceImpl;
import com.project.task.manager.service.implementation.TaskServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Task API", description = "Task CRUD + Comments")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/tasks")
public class TaskController {

    private final TaskServiceImpl taskService;
    private final CommentServiceImpl commentService;

    @GetMapping("/{id}")
    @Operation(summary = "Get Task by ID")
    public ResponseEntity<TaskResponse> findTask(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.readTask(id));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("authentication.principal.id == #userId")
    @Operation(summary = "Get user's tasks (paginated)")
    public ResponseEntity<Page<TaskResponse>> getMyTasks(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(taskService.readTaskPage(userId, page, size));
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Create new Task")
    public ResponseEntity<TaskResponse> createTask(
            @Valid @RequestBody TaskRequest request,
            @RequestParam Long authorId) {
        return ResponseEntity.status(201).body(taskService.create(request, authorId));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("@securityService.canAccessTask(#id)")
    @Operation(summary = "Update task status")
    public ResponseEntity<TaskResponse> updateTaskStatus(
            @PathVariable Long id,
            @RequestBody STATUS status) {
        return ResponseEntity.ok(taskService.updateStatus(status, id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("@securityService.canAccessTask(#id)")
    @Operation(summary = "Update task details")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequest request) {
        return ResponseEntity.ok(taskService.update(request, id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@securityService.canAccessTask(#id)")
    @Operation(summary = "Delete Task (cascade comments)")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{taskId}/comments")
    @PreAuthorize("@securityService.canAccessTask(#taskId)")
    @Operation(summary = "Add comment to task")
    public ResponseEntity<CommentResponse> addComment(
            @PathVariable Long taskId,
            @Valid @RequestBody CommentRequest request,
            @RequestParam Long userId) {
        return ResponseEntity.status(201).body(commentService.create(request, taskId, userId));
    }

    @GetMapping("/{taskId}/comments")
    @PreAuthorize("@securityService.canAccessTask(#taskId)")
    @Operation(summary = "Get task comments (paginated)")
    public ResponseEntity<Page<CommentResponse>> getTaskComments(
            @PathVariable Long taskId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(commentService.readMultiple(taskId, page, size));
    }

    @PutMapping("/{taskId}/comments/{commentId}")
    @PreAuthorize("@securityService.canAccessComment(#commentId)")
    @Operation(summary = "Update comment")
    public ResponseEntity<CommentResponse> updateComment(
            @PathVariable Long taskId,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentRequest request) {
        return ResponseEntity.ok(commentService.update(request, commentId));
    }

    @DeleteMapping("/{taskId}/comments/{commentId}")
    @PreAuthorize("@securityService.canAccessComment(#commentId)")
    @Operation(summary = "Delete comment")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long taskId,
            @PathVariable Long commentId) {
        commentService.delete(commentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{taskId}/comments/{commentId}")
    @PreAuthorize("@securityService.canAccessTask(#taskId)")
    @Operation(summary = "Get single comment")
    public ResponseEntity<CommentResponse> getComment(
            @PathVariable Long taskId,
            @PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.read(commentId));
    }
}