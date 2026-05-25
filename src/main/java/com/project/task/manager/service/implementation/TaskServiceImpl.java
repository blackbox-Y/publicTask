package com.project.task.manager.service.implementation;

import com.project.task.manager.domain.exception.entity.EntityException;
import com.project.task.manager.domain.exception.entity.EntityNotFoundException;
import com.project.task.manager.domain.request.TaskRequest;
import com.project.task.manager.domain.response.TaskResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.project.task.manager.domain.entities.Comment;
import com.project.task.manager.domain.entities.Task;
import com.project.task.manager.domain.entities.User;
import com.project.task.manager.domain.status.STATUS;
import com.project.task.manager.repository.TaskRepository;
import com.project.task.manager.service.map.TaskMapper;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class TaskServiceImpl {

	private final TaskRepository repo;

    private final UserServiceImpl userService;

    private final TaskMapper mapper;


    @PreAuthorize("authentication.principal.id == #authorId")
    public TaskResponse create(TaskRequest request, Long authorId) {
        log.info("creating a new Task for User: {}", authorId);

        User author = userService.findById(authorId);
        Task task = mapper.toEntity(request);

        task.setAuthor(author);

        Task saved = repo.save(task);
        return mapper.toResponse(saved);
    }


    @PreAuthorize("@taskSecurityService.canAccessTask(#id)")
    public TaskResponse readTask (Long id) {
        log.info("reading a task with ID: {}",  id);

        Task task = repo.findById(id).orElseThrow(
				()-> new EntityNotFoundException(EntityException.EntityType.TASK, id));
		return mapper.toResponse(task);
	}

	@PreAuthorize("authentication.principal.id == #id")
	public Page<TaskResponse> readTaskPage (Long id, int pageNumber, int pageSize) {
        log.info("reading a page of tasks. user's ID: {}",  id);

        Pageable pageable = PageRequest.of(
                pageNumber, pageSize,
                Sort.by("id").descending());
        User user = userService.findById(id);
		
		Page <Task> taskPage = repo.findByAuthor(pageable, user);
		return mapper.toResponsePage(taskPage);
	}

    @Transactional
    @PreAuthorize("@taskSecurityService.canAccessTask(#id)")
	public TaskResponse updateStatus(STATUS status, Long id) {
        log.info("updating task status ID: {}",  id);

        Task existingTask =  repo.findById(id).orElseThrow(
                ()-> new EntityNotFoundException(EntityException.EntityType.TASK, id));
        existingTask.setStatus(status);

        Task updated = repo.save(existingTask);
        return  mapper.toResponse(updated);
	}

    @Transactional
    @PreAuthorize("@taskSecurityService.canAccessTask(#id)")
    public TaskResponse update(TaskRequest request, Long id) {
        log.info("updating task ID: {}",  id);

        Task task = repo.findById(id).orElseThrow(
                ()-> new EntityNotFoundException(EntityException.EntityType.TASK, id));

        mapper.updateEntityFromRequest(request, task);
        Task saved = repo.save(task);
        return mapper.toResponse(saved);
    }

    @Transactional
    @PreAuthorize("@taskSecurityService.canAccessTask(#id)")
    public void delete(Long id) {
        log.info("deleting a task with ID: {}", id);

        Task task = repo.findById(id).orElseThrow(
                ()-> new EntityNotFoundException(EntityException.EntityType.TASK, id));

        repo.delete(task);
    }
}
