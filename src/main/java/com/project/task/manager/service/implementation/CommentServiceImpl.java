package com.project.task.manager.service.implementation;

import com.project.task.manager.domain.exception.client.EntityException;
import com.project.task.manager.domain.exception.client.EntityNotFoundException;
import com.project.task.manager.domain.request.CommentRequest;
import com.project.task.manager.domain.response.CommentResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.task.manager.domain.entities.Comment;
import com.project.task.manager.domain.entities.Task;
import com.project.task.manager.domain.entities.User;
import com.project.task.manager.repository.CommentRepository;
import com.project.task.manager.repository.TaskRepository;
import com.project.task.manager.service.map.CommentMapper;

@Slf4j
@Service
@AllArgsConstructor
public class CommentServiceImpl {

    private final CommentRepository repo;
    private final TaskRepository taskRepo;

    private final UserServiceImpl userService;

    private final CommentMapper mapper;

    @Transactional
    @PreAuthorize("@taskSecurityService.canAccessTask(#taskId)")
    public CommentResponse create(CommentRequest request, Long taskId, Long userId) {
        log.info("creating comment: task={}, user={}", taskId, userId);
        Task task = taskRepo.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException(EntityException.EntityType.TASK, taskId));
        User user = userService.findById(userId);
        Comment comment = mapper.toEntity(request);
        comment.setTask(task);
        comment.setCommenter(user);
        return mapper.toResponse(repo.save(comment));
    }

    @PreAuthorize("@taskSecurityService.canAccessComment(#id)")
    public CommentResponse read(Long id) {
        log.info("reading comment ID: {}", id);
        Comment comment = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(EntityException.EntityType.COMMENT, id));
        return mapper.toResponse(comment);
    }

    @PreAuthorize("@taskSecurityService.canAccessTask(#taskId)")
    public Page<CommentResponse> readMultiple(Long taskId, int pageNumber, int pageSize) {
        log.info("reading comments page for task: {}", taskId);
        Task task = taskRepo.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException(EntityException.EntityType.TASK, taskId));
        Pageable pageable = PageRequest.of(pageNumber, pageSize,
                Sort.by("createdAt").descending());
        return mapper.toResponsePage(repo.findByTask(task, pageable));
    }

    @Transactional
    @PreAuthorize("@taskSecurityService.canAccessComment(#id)")
    public CommentResponse update(CommentRequest request, Long id) {
        log.info("updating comment ID: {}", id);
        Comment comment = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(EntityException.EntityType.COMMENT, id));
        mapper.updateEntityFromRequest(request, comment);
        return mapper.toResponse(repo.save(comment));
    }

    @Transactional
    @PreAuthorize("@taskSecurityService.canAccessComment(#id)")
    public void delete(Long id) {
        log.info("deleting comment ID: {}", id);
        repo.deleteById(id);  // ✅ Cascade-safe
    }
}