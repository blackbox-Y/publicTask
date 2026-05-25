package com.project.task.manager.controller;

import com.project.task.manager.domain.response.UserResponse;
import com.project.task.manager.security.SecurityUtils;
import com.project.task.manager.service.implementation.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.ServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.project.task.manager.domain.request.AuthenticationRequest;
import com.project.task.manager.domain.response.AuthenticationResponse;
import com.project.task.manager.domain.request.RegisterRequest;
import com.project.task.manager.service.implementation.AuthenticationService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Tag(name = "Authenticator", description = "User registration and login")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {

	private final AuthenticationService service;

    private final UserServiceImpl userService;

	@PostMapping("/register")
    @Operation (summary = "register new user", description = "returns JWT token")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User created"),
            @ApiResponse(responseCode = "400", description = "User already exists")
    })
	public ResponseEntity<AuthenticationResponse> register (@RequestBody RegisterRequest request) {
        return ResponseEntity
                .status(201).body(service.register(request));
    }

	@PostMapping("/authenticate")
    @Operation (summary = "authenticate user", description = "gains user access")
    @ApiResponses ({
            @ApiResponse(responseCode = "201", description = "Authenticated"),
            @ApiResponse(responseCode = "400", description = "Invalid credentials")
    })
	public ResponseEntity<AuthenticationResponse> authenticate (@RequestBody AuthenticationRequest request) {
        return ResponseEntity.status(201).body(service.authenticate(request));
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    @Operation(description = "Get current user profile")
    public ResponseEntity <UserResponse> getProfile () {
        return ResponseEntity.ok(userService.read(SecurityUtils.getCurrentUserId()));
    }

    @GetMapping("/verify")
    @Operation(summary = "Verify user email")
    public ResponseEntity<String> verify(@RequestParam String token) {
        service.verifyEmail(token);
        return ResponseEntity.ok("Email verified successfully");
    }

    @PostMapping("/reset-password/request")
    public ResponseEntity<String> requestReset(@RequestParam String email) {
        service.requestPasswordReset(email);
        return ResponseEntity.ok("Password reset link sent to email");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> reset(
            @RequestParam String token,
            @RequestBody Map<String, String> body, ServletRequest servletRequest) {
        service.resetPassword(token, body.get("newPassword"));
        return ResponseEntity.ok("Пароль успешно изменён.");
    }
}
