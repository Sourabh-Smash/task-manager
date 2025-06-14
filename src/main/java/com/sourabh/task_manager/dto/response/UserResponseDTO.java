package com.sourabh.task_manager.dto.response;

import com.sourabh.task_manager.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * DTO for user response data (excludes sensitive information)
 */
@Setter
@Getter
public class UserResponseDTO {

    // Getters and Setters
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private UserRole role;
    private Boolean isActive;
    private Boolean isEmailVerified;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLogin;

    // Constructors
    public UserResponseDTO() {}

    public UserResponseDTO(Long id, String username, String email, String firstName,
                           String lastName, UserRole role, Boolean isActive) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.isActive = isActive;
    }

    // Utility method
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
