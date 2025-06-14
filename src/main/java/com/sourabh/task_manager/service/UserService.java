package com.sourabh.task_manager.service;

import com.sourabh.task_manager.dto.request.UserLoginDTO;
import com.sourabh.task_manager.dto.request.UserRegistrationDTO;
import com.sourabh.task_manager.dto.request.UserUpdateDTO;
import com.sourabh.task_manager.dto.response.UserResponseDTO;
import com.sourabh.task_manager.enums.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface for User-related business operations
 */
public interface UserService {

    /**
     * Register a new user
     * @param registrationDTO user registration data
     * @return the created user response DTO
     */
    UserResponseDTO registerUser(UserRegistrationDTO registrationDTO);

    /**
     * Get user by ID
     * @param id user ID
     * @return user response DTO
     */
    UserResponseDTO getUserById(Long id);

    /**
     * Get user by username
     * @param username the username
     * @return user response DTO
     */
    UserResponseDTO getUserByUsername(String username);

    /**
     * Get user by email
     * @param email the email
     * @return user response DTO
     */
    UserResponseDTO getUserByEmail(String email);

    /**
     * Get all users
     * @return list of user response DTOs
     */
    List<UserResponseDTO> getAllUsers();

    /**
     * Get all users with pagination
     * @param pageable pagination information
     * @return page of user response DTOs
     */
    Page<UserResponseDTO> getAllUsers(Pageable pageable);

    /**
     * Get all active users
     * @return list of active user response DTOs
     */
    List<UserResponseDTO> getActiveUsers();

    /**
     * Get users by role
     * @param role the user role
     * @return list of user response DTOs
     */
    List<UserResponseDTO> getUsersByRole(UserRole role);

    /**
     * Search users by name
     * @param searchTerm search term
     * @param pageable pagination information
     * @return page of user response DTOs
     */
    Page<UserResponseDTO> searchUsers(String searchTerm, Pageable pageable);

    /**
     * Update user information
     * @param id user ID
     * @param updateDTO user update data
     * @return updated user response DTO
     */
    UserResponseDTO updateUser(Long id, UserUpdateDTO updateDTO);

    /**
     * Delete user
     * @param id user ID
     */
    void deleteUser(Long id);

    /**
     * Activate user account
     * @param id user ID
     * @return updated user response DTO
     */
    UserResponseDTO activateUser(Long id);

    /**
     * Deactivate user account
     * @param id user ID
     * @return updated user response DTO
     */
    UserResponseDTO deactivateUser(Long id);

    /**
     * Verify user email
     * @param id user ID
     * @return updated user response DTO
     */
    UserResponseDTO verifyEmail(Long id);

    /**
     * Update user role
     * @param id user ID
     * @param role new role
     * @return updated user response DTO
     */
    UserResponseDTO updateUserRole(Long id, UserRole role);

    /**
     * Update user's last login timestamp
     * @param id user ID
     */
    void updateLastLogin(Long id);

    /**
     * Change user password
     * @param id user ID
     * @param currentPassword current password
     * @param newPassword new password
     * @return true if password changed successfully
     */
    boolean changePassword(Long id, String currentPassword, String newPassword);

    /**
     * Validate user login credentials
     * @param loginDTO login credentials
     * @return true if credentials are valid
     */
    boolean validateLogin(UserLoginDTO loginDTO);

    /**
     * Check if username is available
     * @param username the username to check
     * @return true if available
     */
    boolean isUsernameAvailable(String username);

    /**
     * Check if email is available
     * @param email the email to check
     * @return true if available
     */
    boolean isEmailAvailable(String email);

    /**
     * Get total user count
     * @return total number of users
     */
    long getTotalUserCount();

    /**
     * Get active user count
     * @return number of active users
     */
    long getActiveUserCount();

    /**
     * Get user count by role
     * @param role the user role
     * @return number of users with the specified role
     */
    long getUserCountByRole(UserRole role);

    /**
     * Get users who haven't logged in for specified days
     * @param daysSinceLastLogin number of days since last login
     * @return list of inactive users
     */
    List<UserResponseDTO> getInactiveUsers(int daysSinceLastLogin);
}
