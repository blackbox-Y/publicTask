//package com.project.task.manager.service.interfaces;
//
//import com.project.task.manager.domain.request.CommentRequest;
//import com.project.task.manager.domain.request.TaskRequest;
//import com.project.task.manager.domain.request.UserRequest;
//import com.project.task.manager.domain.response.CommentResponse;
//import org.springframework.data.domain.Page;
//
//import com.project.task.manager.domain.entities.Comment;
//
//public interface CommentService {
//
//    Comment create (
//            CommentRequest comment,
//            TaskRequest task,
//            UserRequest user
//    );
//
//	Page <CommentResponse> readMultiple (
//            TaskRequest task,
//			UserRequest user,
//			int pageNumber,
//			int pageSize
//			);
//
//    CommentResponse read (TaskRequest task, String email);
//
//
//}
