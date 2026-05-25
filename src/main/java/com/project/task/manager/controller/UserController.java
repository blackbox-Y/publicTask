package com.project.task.manager.controller;


import com.project.task.manager.domain.request.UserRequest;
import com.project.task.manager.domain.response.UserResponse;
import com.project.task.manager.security.SecurityUtils;
import com.project.task.manager.service.implementation.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "User API", description = "User management (admin + self)")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
public class UserController {
    private final UserServiceImpl service;

    @GetMapping("/{id}")
    @PreAuthorize("@securityService.canAccessUser(#id)")
    @Operation(summary = "Get user profile")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(service.read(id));
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get my profile")
    public ResponseEntity<UserResponse> getMyProfile() {
        Long userId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(service.read(userId));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("authentication.principal.id == #id")
    @Operation(summary = "Update my profile")
    public ResponseEntity<UserResponse> updateProfile(
            @PathVariable Long id,
            @Valid @RequestBody UserRequest request) {
        return ResponseEntity.ok(service.update(request, id));
    }

//    @PatchMapping("/{id}/password")
//    @PreAuthorize("authentication.principal.id == #id")
//    @Operation(summary = "Change my password")
//    public ResponseEntity<Void> changePassword(
//            @PathVariable Long id,
//            @RequestBody @Valid ChangePasswordRequest request) {
//        // TODO: validate oldPassword == current
//        service.updatePassword(id, request.getNewPassword());
//        return ResponseEntity.ok().build();
//    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Admin create user")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest request) {
        return ResponseEntity.status(201).body(service.create(request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Admin delete user")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

//    @PostMapping("/reset-password")
//    @Operation(summary = "Send password reset email")
//    public ResponseEntity<Void> requestPasswordReset(@RequestParam String email) {
//        service.sendResetEmail(email);  // TODO: implement
//        return ResponseEntity.ok().build();
//    }

//    @PostMapping("/{id}/avatar")
//    @PreAuthorize("authentication.principal.id == #id")
//    @Operation(summary = "Upload user avatar")
//    public ResponseEntity<String> uploadAvatar(
//            @PathVariable Long id,
//            @RequestParam("file") MultipartFile file) {
//        String url = service.uploadAvatar(id, file);  // S3/MinIO
//        return ResponseEntity.ok(url);
//    }

}
