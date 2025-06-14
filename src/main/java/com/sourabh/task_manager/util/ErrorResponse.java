package com.sourabh.task_manager.util;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Standard error response format for the application
 */
@Setter
@Getter
public class ErrorResponse {
    // Getters and Setters
    private int status;
    private String message;
    private LocalDateTime timestamp;
    private String path;
    private Map<String, String> validationErrors;

    public ErrorResponse() {}

    public ErrorResponse(int status, String message, LocalDateTime timestamp, String path) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
        this.path = path;
    }

}
