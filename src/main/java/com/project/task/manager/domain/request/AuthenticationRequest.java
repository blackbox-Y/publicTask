package com.project.task.manager.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Authentication request")
public class AuthenticationRequest {

    @Schema(description = "User email", example = "user@example.com")
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Schema(description = "Password", example = "password123")
    @NotBlank(message = "Password is required")
    private String password;
}