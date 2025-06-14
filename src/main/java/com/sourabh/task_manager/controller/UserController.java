package com.sourabh.task_manager.controller;

import com.sourabh.task_manager.dto.request.UserLoginDTO;
import com.sourabh.task_manager.dto.request.UserRegistrationDTO;
import com.sourabh.task_manager.dto.request.UserUpdateDTO;
import com.sourabh.task_manager.dto.response.UserResponseDTO;
import com.sourabh.task_manager.enums.UserRole;
import com.sourabh.task_manager.util.ApiResponse;
import com.sourabh.task_manager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for User management operations
 * Provides endpoints for user CRUD operations and authentication
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Register a new user
     * POST /api/users/register
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDTO>> registerUser(
            @Valid @RequestBody UserRegistrationDTO registrationDTO) {
        UserResponseDTO user = userService.registerUser(registrationDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "User registered successfully", user));
    }

    /**
     * User login validation
     * POST /api/users/login
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, Object>>> loginUser(
            @Valid @RequestBody UserLoginDTO loginDTO) {
        boolean isValid = userService.validateLogin(loginDTO);

        if (isValid) {
            // In a real application, you would generate JWT token here
            Map<String, Object> response = new HashMap<>();
            response.put("authenticated", true);
            response.put("message", "Login successful");
            // response.put("token", jwtToken); // Add JWT token generation

            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Login successful", response));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(false, "Invalid credentials", null));
        }
    }

    /**
     * Get user by ID
     * GET /api/users/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUserById(@PathVariable Long id) {
        UserResponseDTO user = userService.getUserById(id);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "User retrieved successfully", user));
    }

    /**
     * Get user by username
     * GET /api/users/username/{username}
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUserByUsername(
            @PathVariable String username) {
        UserResponseDTO user = userService.getUserByUsername(username);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "User retrieved successfully", user));
    }

    /**
     * Get all users with pagination
     * GET /api/users?page=0&size=10&sort=createdAt,desc
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<UserResponseDTO>>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<UserResponseDTO> users = userService.getAllUsers(pageable);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Users retrieved successfully", users));
    }

    /**
     * Get all active users
     * GET /api/users/active
     */
    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getActiveUsers() {
        List<UserResponseDTO> users = userService.getActiveUsers();
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Active users retrieved successfully", users));
    }

    /**
     * Get users by role
     * GET /api/users/role/{role}
     */
    @GetMapping("/role/{role}")
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getUsersByRole(
            @PathVariable UserRole role) {
        List<UserResponseDTO> users = userService.getUsersByRole(role);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Users retrieved successfully", users));
    }

    /**
     * Search users
     * GET /api/users/search?q=searchTerm&page=0&size=10
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<UserResponseDTO>>> searchUsers(
            @RequestParam String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<UserResponseDTO> users = userService.searchUsers(q, pageable);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Search completed successfully", users));
    }

    /**
     * Update user
     * PUT /api/users/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateDTO updateDTO) {
        UserResponseDTO user = userService.updateUser(id, updateDTO);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "User updated successfully", user));
    }

    /**
     * Delete user
     * DELETE /api/users/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "User deleted successfully", null));
    }

    /**
     * Activate user
     * PATCH /api/users/{id}/activate
     */
    @PatchMapping("/{id}/activate")
    public ResponseEntity<ApiResponse<UserResponseDTO>> activateUser(@PathVariable Long id) {
        UserResponseDTO user = userService.activateUser(id);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "User activated successfully", user));
    }

    /**
     * Deactivate user
     * PATCH /api/users/{id}/deactivate
     */
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<UserResponseDTO>> deactivateUser(@PathVariable Long id) {
        UserResponseDTO user = userService.deactivateUser(id);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "User deactivated successfully", user));
    }

    /**
     * Verify user email
     * PATCH /api/users/{id}/verify-email
     */
    @PatchMapping("/{id}/verify-email")
    public ResponseEntity<ApiResponse<UserResponseDTO>> verifyEmail(@PathVariable Long id) {
        UserResponseDTO user = userService.verifyEmail(id);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Email verified successfully", user));
    }

    /**
     * Update user role
     * PATCH /api/users/{id}/role
     */
    @PatchMapping("/{id}/role")
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateUserRole(
            @PathVariable Long id,
            @RequestBody Map<String, String> roleRequest) {
        UserRole role = UserRole.valueOf(roleRequest.get("role"));
        UserResponseDTO user = userService.updateUserRole(id, role);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "User role updated successfully", user));
    }

    /**
     * Change password
     * PATCH /api/users/{id}/change-password
     */
    @PatchMapping("/{id}/change-password")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            @PathVariable Long id,
            @RequestBody Map<String, String> passwordRequest) {
        String currentPassword = passwordRequest.get("currentPassword");
        String newPassword = passwordRequest.get("newPassword");

        boolean success = userService.changePassword(id, currentPassword, newPassword);
        if (success) {
            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Password changed successfully", null));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Invalid current password", null));
        }
    }

    /**
     * Check username availability
     * GET /api/users/check-username/{username}
     */
    @GetMapping("/check-username/{username}")
    public ResponseEntity<ApiResponse<Map<String, Boolean>>> checkUsernameAvailability(
            @PathVariable String username) {
        boolean available = userService.isUsernameAvailable(username);
        Map<String, Boolean> response = new HashMap<>();
        response.put("available", available);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Username availability checked", response));
    }

    /**
     * Check email availability
     * GET /api/users/check-email/{email}
     */
    @GetMapping("/check-email/{email}")
    public ResponseEntity<ApiResponse<Map<String, Boolean>>> checkEmailAvailability(
            @PathVariable String email) {
        boolean available = userService.isEmailAvailable(email);
        Map<String, Boolean> response = new HashMap<>();
        response.put("available", available);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Email availability checked", response));
    }

    /**
     * Get user statistics
     * GET /api/users/stats
     */
    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getUserStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", userService.getTotalUserCount());
        stats.put("activeUsers", userService.getActiveUserCount());
        stats.put("adminUsers", userService.getUserCountByRole(UserRole.ADMIN));
        stats.put("managerUsers", userService.getUserCountByRole(UserRole.MANAGER));
        stats.put("regularUsers", userService.getUserCountByRole(UserRole.USER));

        return ResponseEntity.ok(
                new ApiResponse<>(true, "User statistics retrieved", stats));
    }

    /**
     * Get inactive users
     * GET /api/users/inactive?days=30
     */
    @GetMapping("/inactive")
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getInactiveUsers(
            @RequestParam(defaultValue = "30") int days) {
        List<UserResponseDTO> users = userService.getInactiveUsers(days);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Inactive users retrieved", users));
    }
}
