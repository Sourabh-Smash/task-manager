package com.sourabh.task_manager.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for user login requests
 */
@Setter
@Getter
public class UserLoginDTO {

    // Getters and Setters
    @NotBlank(message = "Username or email is required")
    private String usernameOrEmail;

    @NotBlank(message = "Password is required")
    private String password;

    // Constructors
    public UserLoginDTO() {}

    public UserLoginDTO(String usernameOrEmail, String password) {
        this.usernameOrEmail = usernameOrEmail;
        this.password = password;
    }

}
