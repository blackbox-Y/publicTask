package com.project.task.manager.domain.response;

import com.project.task.manager.domain.status.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response DTO with user information")
public class UserResponse {

    @Schema(description = "User ID", example = "1")
    private Long id;

    @Schema(description = "User first name", example = "Ivan")
    private String name;

    @Schema(description = "User last name", example = "Ivanov")
    private String surname;

    @Schema(description = "User email", example = "ivan@example.com")
    private String email;

    @Schema(description = "User role", allowableValues = {"USER", "ADMIN", "MODERATOR"})
    private Role role;
}