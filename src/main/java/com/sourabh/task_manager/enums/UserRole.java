package com.sourabh.task_manager.enums;

/**
 * Enum representing different user roles in the task management system
 */
public enum UserRole {
    ADMIN("Administrator"),
    MANAGER("Manager"),
    USER("Regular User"),
    GUEST("Guest User");

    private final String displayName;

    UserRole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
