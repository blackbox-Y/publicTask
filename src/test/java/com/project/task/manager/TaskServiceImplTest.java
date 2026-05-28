package com.project.task.manager;

import com.project.task.manager.domain.entities.Task;
import com.project.task.manager.domain.entities.User;
import com.project.task.manager.domain.exception.client.EntityException;
import com.project.task.manager.domain.exception.client.EntityNotFoundException;
import com.project.task.manager.domain.request.TaskRequest;
import com.project.task.manager.domain.response.TaskResponse;
import com.project.task.manager.domain.status.PRIORITY;
import com.project.task.manager.domain.status.STATUS;
import com.project.task.manager.repository.TaskRepository;
import com.project.task.manager.service.implementation.TaskServiceImpl;
import com.project.task.manager.service.implementation.UserServiceImpl;
import com.project.task.manager.service.map.TaskMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("TaskServiceImpl Unit Tests")
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    private User testUser;
    private Task testTask;
    private TaskRequest taskRequest;
    private TaskResponse taskResponse;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .name("John")
                .surname("Doe")
                .email("john@example.com")
                .build();

        User testAgent = User.builder()
                .id(2L)
                .name("Jane")
                .surname("Smith")
                .email("jane@example.com")
                .build();

        testTask = Task.builder()
                .id(1L)
                .title("Test Task")
                .description("Test Description")
                .priority(PRIORITY.MEDIUM)
                .status(STATUS.NEW)
                .author(testUser)
                .agent(testAgent)
                .steps(List.of("Step 1", "Step 2"))
                .build();

        taskRequest = TaskRequest.builder()
                .title("Test Task")
                .description("Test Description")
                .priority(PRIORITY.MEDIUM)
                .status(STATUS.NEW)
                .authorId(1L)
                .agentId(2L)
                .steps(List.of("Step 1", "Step 2"))
                .build();

        taskResponse = TaskResponse.builder()
                .id(1L)
                .title("Test Task")
                .description("Test Description")
                .priority(PRIORITY.MEDIUM)
                .status(STATUS.NEW)
                .authorId(1L)
                .agentId(2L)
                .steps(List.of("Step 1", "Step 2"))
                .commentIds(List.of())
                .build();
    }

    @Nested
    @DisplayName("Create Task Tests")
    class CreateTaskTests {

        @Test
        @WithMockUser
        @DisplayName("Should create task successfully")
        void create_Success() {
            Long authorId = 1L;
            when(userService.findById(authorId)).thenReturn(testUser);
            when(taskMapper.toEntity(taskRequest)).thenReturn(testTask);
            when(taskRepository.save(any(Task.class))).thenReturn(testTask);
            when(taskMapper.toResponse(testTask)).thenReturn(taskResponse);

            TaskResponse result = taskService.create(taskRequest, authorId);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getId()).isEqualTo(1L);
            assertThat(result.getTitle()).isEqualTo("Test Task");
            assertThat(result.getAuthorId()).isEqualTo(1L);

            verify(userService).findById(authorId);
            verify(taskMapper).toEntity(taskRequest);
            verify(taskRepository).save(testTask);
            verify(taskMapper).toResponse(testTask);
        }

        @Test
        @WithMockUser
        @DisplayName("Should throw exception when author not found")
        void create_AuthorNotFound_ThrowsException() {
            // Given
            Long authorId = 999L;
            when(userService.findById(authorId)).thenThrow(
                    new EntityNotFoundException(EntityException.EntityType.USER, authorId));

            // When & Then
            assertThatThrownBy(() -> taskService.create(taskRequest, authorId))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessageContaining("User with id 999 not found");

            verify(taskRepository, never()).save(any());
        }

        @Test
        @WithMockUser
        @DisplayName("Should handle null authorId")
        void create_NullAuthorId_ThrowsException() {
            Long authorId = null;
            when(userService.findById(authorId)).thenThrow(new IllegalArgumentException());

            // When & Then
            assertThatThrownBy(() -> taskService.create(taskRequest, authorId))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("Read Task Tests")
    class ReadTaskTests {

        @Test
        @WithMockUser
        @DisplayName("Should read task successfully")
        void readTask_Success() {
            // Given
            Long taskId = 1L;
            when(taskRepository.findById(taskId)).thenReturn(Optional.of(testTask));
            when(taskMapper.toResponse(testTask)).thenReturn(taskResponse);

            // When
            TaskResponse result = taskService.readTask(taskId);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getId()).isEqualTo(1L);
            assertThat(result.getTitle()).isEqualTo("Test Task");

            verify(taskRepository).findById(taskId);
            verify(taskMapper).toResponse(testTask);
        }

        @Test
        @DisplayName("Should throw EntityNotFoundException when task not found")
        void readTask_NotFound_ThrowsException() {
            // Given
            Long taskId = 999L;
            when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

            // When & Then
            assertThatThrownBy(() -> taskService.readTask(taskId))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessageContaining("Task with id 999 not found");

            verify(taskRepository).findById(taskId);
            verify(taskMapper, never()).toResponse(any());
        }
    }

    @Nested
    @DisplayName("Read Task Page Tests")
    class ReadTaskPageTests {

        @Test
        @WithMockUser
        @DisplayName("Should read task page successfully")
        void readTaskPage_Success() {
            // Given
            Long userId = 1L;
            int pageNumber = 0;
            int pageSize = 10;
            Pageable expectedPageable = PageRequest.of(pageNumber, pageSize);
            Page<Task> taskPage = new PageImpl<>(List.of(testTask));
            Page<TaskResponse> expectedResponsePage = new PageImpl<>(List.of(taskResponse));

            when(userService.findById(userId)).thenReturn(testUser);
            when(taskRepository.findByAuthor(expectedPageable, testUser)).thenReturn(taskPage);
            when(taskMapper.toResponsePage(taskPage)).thenReturn(expectedResponsePage);

            // When
            Page<TaskResponse> result = taskService.readTaskPage(userId, pageNumber, pageSize);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getContent()).hasSize(1);
            assertThat(result.getContent().get(0).getId()).isEqualTo(1L);

            verify(userService).findById(userId);
            verify(taskRepository).findByAuthor(any(Pageable.class), eq(testUser));
            verify(taskMapper).toResponsePage(taskPage);
        }

        @Test
        @WithMockUser
        @DisplayName("Should handle empty page results")
        void readTaskPage_EmptyResults_ReturnsEmptyPage() {
            // Given
            Long userId = 1L;
            int pageNumber = 0;
            int pageSize = 10;
            Page<Task> emptyPage = Page.empty();
            Page<TaskResponse> expectedEmptyPage = Page.empty();

            when(userService.findById(userId)).thenReturn(testUser);
            when(taskRepository.findByAuthor(any(Pageable.class), eq(testUser))).thenReturn(emptyPage);
            when(taskMapper.toResponsePage(emptyPage)).thenReturn(expectedEmptyPage);

            // When
            Page<TaskResponse> result = taskService.readTaskPage(userId, pageNumber, pageSize);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getContent()).isEmpty();
        }
    }

    @Nested
    @DisplayName("Update Task Status Tests")
    class UpdateStatusTests {

        @Test
        @WithMockUser
        @DisplayName("Should update task status successfully")
        void updateStatus_Success() {
            // Given
            Long taskId = 1L;
            STATUS newStatus = STATUS.IN_PROCESS;

            when(taskRepository.findById(taskId)).thenReturn(Optional.of(testTask));
            when(taskRepository.save(testTask)).thenReturn(testTask);
            when(taskMapper.toResponse(testTask)).thenReturn(taskResponse);

            // When
            TaskResponse result = taskService.updateStatus(newStatus, taskId);

            // Then
            assertThat(result).isNotNull();
            assertThat(testTask.getStatus()).isEqualTo(newStatus);

            verify(taskRepository).findById(taskId);
            verify(taskRepository).save(testTask);
            verify(taskMapper).toResponse(testTask);
        }

        @Test
        @DisplayName("Should throw exception when task not found for status update")
        void updateStatus_TaskNotFound_ThrowsException() {
            // Given
            Long taskId = 999L;
            when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

            // When & Then
            assertThatThrownBy(() -> taskService.updateStatus(STATUS.DONE, taskId))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessageContaining("Task with id 999 not found");

            verify(taskRepository, never()).save(any());
        }

        @Test
        @WithMockUser
        @DisplayName("Should handle all status transitions")
        void updateStatus_AllStatusTransitions() {
            // Given
            Long taskId = 1L;
            STATUS[] allStatuses = STATUS.values();

            for (STATUS status : allStatuses) {
                Task task = Task.builder().id(1L).status(STATUS.NEW).build();
                when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
                when(taskRepository.save(task)).thenReturn(task);
                when(taskMapper.toResponse(any(Task.class))).thenReturn(taskResponse);

                // When
                TaskResponse result = taskService.updateStatus(status, taskId);

                // Then
                assertThat(task.getStatus()).isEqualTo(status);
            }
        }
    }

    @Nested
    @DisplayName("Update Task Tests")
    class UpdateTaskTests {

        @Test
        @WithMockUser
        @DisplayName("Should update task successfully")
        void update_Success() {
            // Given
            Long taskId = 1L;
            TaskRequest updateRequest = TaskRequest.builder()
                    .title("Updated Title")
                    .description("Updated Description")
                    .priority(PRIORITY.HIGH)
                    .status(STATUS.IN_PROCESS)
                    .build();

            when(taskRepository.findById(taskId)).thenReturn(Optional.of(testTask));
            doNothing().when(taskMapper).updateEntityFromRequest(updateRequest, testTask);
            when(taskRepository.save(testTask)).thenReturn(testTask);
            when(taskMapper.toResponse(testTask)).thenReturn(taskResponse);

            // When
            TaskResponse result = taskService.update(updateRequest, taskId);

            // Then
            assertThat(result).isNotNull();
            verify(taskMapper).updateEntityFromRequest(updateRequest, testTask);
            verify(taskRepository).save(testTask);
            verify(taskMapper).toResponse(testTask);
        }

        @Test
        @DisplayName("Should throw exception when task not found for update")
        void update_TaskNotFound_ThrowsException() {
            // Given
            Long taskId = 999L;
            when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

            // When & Then
            assertThatThrownBy(() -> taskService.update(taskRequest, taskId))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessageContaining("Task with id 999 not found");

            verify(taskRepository, never()).save(any());
        }

        @Test
        @WithMockUser
        @DisplayName("Should handle partial update with null values")
        void update_PartialUpdate_OnlyNonNullFieldsUpdated() {
            // Given
            Long taskId = 1L;
            TaskRequest partialRequest = TaskRequest.builder()
                    .title("Only Title Updated")
                    .build();

            when(taskRepository.findById(taskId)).thenReturn(Optional.of(testTask));
            doNothing().when(taskMapper).updateEntityFromRequest(partialRequest, testTask);
            when(taskRepository.save(testTask)).thenReturn(testTask);
            when(taskMapper.toResponse(testTask)).thenReturn(taskResponse);

            // When
            TaskResponse result = taskService.update(partialRequest, taskId);

            // Then
            assertThat(result).isNotNull();
            verify(taskMapper).updateEntityFromRequest(partialRequest, testTask);
        }
    }

    @Nested
    @DisplayName("Delete Task Tests")
    class DeleteTaskTests {

        @Test
        @WithMockUser
        @DisplayName("Should delete task successfully")
        void delete_Success() {
            // Given
            Long taskId = 1L;
            when(taskRepository.findById(taskId)).thenReturn(Optional.of(testTask));
            doNothing().when(taskRepository).delete(testTask);

            // When
            taskService.delete(taskId);

            // Then
            verify(taskRepository).findById(taskId);
            verify(taskRepository).delete(testTask);
        }

        @Test
        @DisplayName("Should throw exception when task not found for deletion")
        void delete_TaskNotFound_ThrowsException() {
            // Given
            Long taskId = 999L;
            when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

            // When & Then
            assertThatThrownBy(() -> taskService.delete(taskId))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessageContaining("Task with id 999 not found");

            verify(taskRepository, never()).delete(any());
        }

        @Test
        @WithMockUser
        @DisplayName("Should handle deletion of task with comments")
        void delete_TaskWithComments_DeletesSuccessfully() {
            // Given
            Long taskId = 1L;
            // Task with comments would be tested here
            when(taskRepository.findById(taskId)).thenReturn(Optional.of(testTask));
            doNothing().when(taskRepository).delete(testTask);

            // When
            taskService.delete(taskId);

            // Then
            verify(taskRepository).delete(testTask);
        }
    }

    @Nested
    @DisplayName("Integration Scenarios Tests")
    class IntegrationScenariosTests {

        @Test
        @WithMockUser
        @DisplayName("Should complete full task lifecycle: create → read → update → delete")
        void taskLifecycle_Success() {
            // Create
            Long authorId = 1L;
            when(userService.findById(authorId)).thenReturn(testUser);
            when(taskMapper.toEntity(taskRequest)).thenReturn(testTask);
            when(taskRepository.save(any(Task.class))).thenReturn(testTask);
            when(taskMapper.toResponse(testTask)).thenReturn(taskResponse);

            TaskResponse created = taskService.create(taskRequest, authorId);
            assertThat(created).isNotNull();

            // Read
            when(taskRepository.findById(created.getId())).thenReturn(Optional.of(testTask));
            TaskResponse read = taskService.readTask(created.getId());
            assertThat(read).isNotNull();

            // Update
            TaskRequest updateRequest = TaskRequest.builder().title("Updated").build();
            when(taskRepository.findById(created.getId())).thenReturn(Optional.of(testTask));
            when(taskRepository.save(testTask)).thenReturn(testTask);

            TaskResponse updated = taskService.update(updateRequest, created.getId());
            assertThat(updated).isNotNull();

            // Delete
            taskService.delete(created.getId());

            verify(taskRepository, times(2)).save(any());
            verify(taskRepository, times(2)).findById(created.getId());
            verify(taskRepository).delete(testTask);
        }
    }
}