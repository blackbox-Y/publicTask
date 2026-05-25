package com.project.task.manager.domain.request;

import com.project.task.manager.domain.status.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User registration request")
public class RegisterRequest {

    @Schema(description = "First name", example = "Ivan")
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50)
    private String name;

    @Schema(description = "Last name", example = "Ivanov")
    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50)
    private String surname;

    @Schema(description = "Email", example = "ivan@example.com")
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Schema(description = "Password", example = "password123")
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @Schema(description = "Role", allowableValues = {"USER", "ADMIN"})
    private Role role;
}