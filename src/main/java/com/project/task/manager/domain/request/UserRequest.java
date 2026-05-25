package com.project.task.manager.domain.request;

import com.project.task.manager.domain.status.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO for creating/updating a user")
public class UserRequest {

    @Schema(description = "User first name", example = "Ivan")
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50)
    private String name;

    @Schema(description = "User last name", example = "Ivanov")
    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50)
    private String surname;

    @Schema(description = "User email", example = "ivan@example.com")
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;


    @Schema(description = "Password (only when creating/updating)", example = "password123")
    @NotBlank(groups = Create.class, message = "Password is required when creating")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @Schema(description = "User role", allowableValues = {"USER", "ADMIN", "MODERATOR"})
    private Role role;

    // required when creating a user
    public interface Create {}
}