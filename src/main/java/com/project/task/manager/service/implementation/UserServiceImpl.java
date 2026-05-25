package com.project.task.manager.service.implementation;

import com.project.task.manager.domain.exception.entity.EntityException;
import com.project.task.manager.domain.exception.entity.EntityNotFoundException;
import com.project.task.manager.domain.exception.user.UserAlreadyExistsException;
import com.project.task.manager.domain.request.UserRequest;
import com.project.task.manager.domain.response.UserResponse;
import com.project.task.manager.domain.status.Role;
import com.project.task.manager.service.map.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.task.manager.domain.exception.*;
import com.project.task.manager.domain.entities.User;
import com.project.task.manager.repository.UserRepository;
import lombok.AllArgsConstructor;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl{

	private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final UserMapper mapper;

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public UserResponse create(UserRequest request) {
        log.info("admin creating user: {}", request.getEmail());

        if (repo.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException(request.getEmail());
        }

        User user = User.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        return mapper.toResponse(repo.save(user));
    }

    @PreAuthorize("@taskSecurityService.canAccessUser(#id)")
    public UserResponse read(Long id) {
        log.info("reading user ID: {}", id);

        User user = repo.findById(id).orElseThrow(
                () -> new EntityNotFoundException(EntityException.EntityType.USER, id));
        return mapper.toResponse(user);
    }

    @PreAuthorize("authentication.principal.id == #id")
    @Transactional
    public UserResponse update(UserRequest request, Long id) {
        log.info("updating user ID: {}", id);

        User user = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(EntityException.EntityType.USER, id));

        mapper.updateEntityFromRequest(request, user);
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(encoder.encode(request.getPassword()));
        }

        return mapper.toResponse(repo.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void delete(Long id) {
        log.info("admin deleting user ID: {}", id);
        repo.deleteById(id);
    }

    public User findById(Long id) {
        return repo.findById(id).orElseThrow(
                ()-> new EntityNotFoundException(EntityException.EntityType.USER, id));
    }
    public User findByEmail(String email) {
        return repo.findByEmail(email).orElseThrow(
                ()-> new EntityNotFoundException(EntityException.EntityType.USER, email));
    }
}
