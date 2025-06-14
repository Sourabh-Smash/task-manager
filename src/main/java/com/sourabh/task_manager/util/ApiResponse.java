package com.sourabh.task_manager.util;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class ApiResponse<T> {
    // Getters and Setters
    private boolean success;
    private String message;
    private T data;
    private Long timestamp;

    public ApiResponse() {
        this.timestamp = System.currentTimeMillis();
    }

    public ApiResponse(boolean success, String message, T data) {
        this();
        this.success = success;
        this.message = message;
        this.data = data;
    }

}
