package com.sourabh.task_manager.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for user update requests
 */
@Setter
@Getter
public class UserUpdateDTO {

    // Getters and Setters
    @Email(message = "Email should be valid")
    private String email;

    @Size(max = 50, message = "First name cannot exceed 50 characters")
    private String firstName;

    @Size(max = 50, message = "Last name cannot exceed 50 characters")
    private String lastName;

    @Size(max = 20, message = "Phone number cannot exceed 20 characters")
    private String phoneNumber;

    // Constructors
    public UserUpdateDTO() { /* TODO document why this constructor is empty */ }

}
