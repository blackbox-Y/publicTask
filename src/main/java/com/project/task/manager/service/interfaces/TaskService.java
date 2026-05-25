//package com.project.task.manager.service.interfaces;
//
//import com.project.task.manager.domain.response.TaskResponse;
//import org.springframework.data.domain.Page;
//
//import com.project.task.manager.domain.entities.Comment;
//import com.project.task.manager.domain.entities.Task;
//import com.project.task.manager.domain.status.STATUS;
//
//public interface TaskService {
//
//	TaskResponse readTask (Long taskId);
//
//	Task findTask (Long taskId, Long authorId);
//
//	Page <TaskDTO> findAuthorTasks (Long authorId, int pageNumber, int pageSize);
//
//	String setStatus (STATUS status, Long taskId, Long authorId);
//
//	Comment addComments (CommentDTO comDTO, Long taskId, Long authorId);
//
//	Page <CommentDTO> showTaskComments (Long taskId, Long authorId, int pageNumber, int pageSize);
//}
