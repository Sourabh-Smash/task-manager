package com.sourabh.task_manager.repository;

import com.sourabh.task_manager.entity.UserEntity;
import com.sourabh.task_manager.enums.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for User entity operations
 * Extends JpaRepository to provide CRUD operations and custom query methods
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Find user by username
     * @param username the username to search for
     * @return Optional containing the user if found
     */
    Optional<UserEntity> findByUsername(String username);

    /**
     * Find user by email
     * @param email the email to search for
     * @return Optional containing the user if found
     */
    Optional<UserEntity> findByEmail(String email);

    /**
     * Find user by username or email (useful for login)
     * @param username the username to search for
     * @param email the email to search for
     * @return Optional containing the user if found
     */
    Optional<UserEntity> findByUsernameOrEmail(String username, String email);

    /**
     * Check if username exists
     * @param username the username to check
     * @return true if exists, false otherwise
     */
    boolean existsByUsername(String username);

    /**
     * Check if email exists
     * @param email the email to check
     * @return true if exists, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Find all active users
     * @return List of active users
     */
    List<UserEntity> findByIsActiveTrue();

    /**
     * Find all inactive users
     * @return List of inactive users
     */
    List<UserEntity> findByIsActiveFalse();

    /**
     * Find users by role
     * @param role the user role to filter by
     * @return List of users with the specified role
     */
    List<UserEntity> findByRole(UserRole role);

    /**
     * Find active users by role
     * @param role the user role to filter by
     * @return List of active users with the specified role
     */
    List<UserEntity> findByRoleAndIsActiveTrue(UserRole role);

    /**
     * Find users by email verification status
     * @param isEmailVerified the email verification status
     * @return List of users with the specified verification status
     */
    List<UserEntity> findByIsEmailVerified(Boolean isEmailVerified);

    /**
     * Find users created between dates
     * @param startDate the start date
     * @param endDate the end date
     * @return List of users created between the specified dates
     */
    List<UserEntity> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Find users by first name and last name (case-insensitive)
     * @param firstName the first name to search for
     * @param lastName the last name to search for
     * @return List of users matching the name criteria
     */
    List<UserEntity> findByFirstNameIgnoreCaseAndLastNameIgnoreCase(String firstName, String lastName);

    /**
     * Search users by name (first name or last name contains the search term)
     * @param searchTerm the term to search for in names
     * @param pageable pagination information
     * @return Page of users matching the search criteria
     */
    @Query("SELECT u FROM UserEntity u WHERE " +
            "LOWER(u.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(u.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(u.username) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<UserEntity> searchUsersByName(@Param("searchTerm") String searchTerm, Pageable pageable);

    /**
     * Find active users with pagination
     * @param pageable pagination information
     * @return Page of active users
     */
    Page<UserEntity> findByIsActiveTrue(Pageable pageable);

    /**
     * Update user's last login time
     * @param userId the user ID
     * @param lastLogin the last login timestamp
     */
    @Modifying
    @Query("UPDATE UserEntity u SET u.lastLogin = :lastLogin WHERE u.id = :userId")
    void updateLastLogin(@Param("userId") Long userId, @Param("lastLogin") LocalDateTime lastLogin);

    /**
     * Update user's active status
     * @param userId the user ID
     * @param isActive the active status
     */
    @Modifying
    @Query("UPDATE UserEntity u SET u.isActive = :isActive WHERE u.id = :userId")
    void updateUserActiveStatus(@Param("userId") Long userId, @Param("isActive") Boolean isActive);

    /**
     * Update user's email verification status
     * @param userId the user ID
     * @param isEmailVerified the email verification status
     */
    @Modifying
    @Query("UPDATE UserEntity u SET u.isEmailVerified = :isEmailVerified WHERE u.id = :userId")
    void updateEmailVerificationStatus(@Param("userId") Long userId, @Param("isEmailVerified") Boolean isEmailVerified);

    /**
     * Count users by role
     * @param role the user role
     * @return count of users with the specified role
     */
    long countByRole(UserRole role);

    /**
     * Count active users
     * @return count of active users
     */
    long countByIsActiveTrue();

    /**
     * Count users registered today
     * @param startOfDay start of the current day
     * @param endOfDay end of the current day
     * @return count of users registered today
     */
    @Query("SELECT COUNT(u) FROM UserEntity u WHERE u.createdAt BETWEEN :startOfDay AND :endOfDay")
    long countUsersRegisteredToday(@Param("startOfDay") LocalDateTime startOfDay,
                                   @Param("endOfDay") LocalDateTime endOfDay);

    /**
     * Find users who haven't logged in for a specified number of days
     * @param cutoffDate the cutoff date for last login
     * @return List of inactive users based on login activity
     */
    @Query("SELECT u FROM UserEntity u WHERE u.lastLogin IS NULL OR u.lastLogin < :cutoffDate")
    List<UserEntity> findInactiveUsersSince(@Param("cutoffDate") LocalDateTime cutoffDate);
}