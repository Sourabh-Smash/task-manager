package com.sourabh.task_manager.mapper;

import com.sourabh.task_manager.dto.request.UserRegistrationDTO;
import com.sourabh.task_manager.dto.response.UserResponseDTO;
import com.sourabh.task_manager.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper class for converting between User entity and DTOs
 * Handles the mapping logic to keep controllers and services clean
 */
@Component
public class UserMapper {

    /**
     * Convert UserRegistrationDTO to User entity
     * @param registrationDTO the registration DTO
     * @return User entity
     */
    public UserEntity toEntity(UserRegistrationDTO registrationDTO) {
        if (registrationDTO == null) {
            return null;
        }

        UserEntity user = new UserEntity();
        user.setUsername(registrationDTO.getUsername());
        user.setEmail(registrationDTO.getEmail());
        user.setPassword(registrationDTO.getPassword()); // Will be encoded in service
        user.setFirstName(registrationDTO.getFirstName());
        user.setLastName(registrationDTO.getLastName());
        user.setPhoneNumber(registrationDTO.getPhoneNumber());

        return user;
    }

    /**
     * Convert User entity to UserResponseDTO
     * @param user the user entity
     * @return UserResponseDTO
     */
    public UserResponseDTO toResponseDTO(UserEntity user) {
        if (user == null) {
            return null;
        }

        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setId(user.getId());
        responseDTO.setUsername(user.getUsername());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setFirstName(user.getFirstName());
        responseDTO.setLastName(user.getLastName());
        responseDTO.setPhoneNumber(user.getPhoneNumber());
        responseDTO.setRole(user.getRole());
        responseDTO.setIsActive(user.getIsActive());
        responseDTO.setIsEmailVerified(user.getIsEmailVerified());
        responseDTO.setCreatedAt(user.getCreatedAt());
        responseDTO.setUpdatedAt(user.getUpdatedAt());
        responseDTO.setLastLogin(user.getLastLogin());

        return responseDTO;
    }

    /**
     * Convert list of User entities to list of UserResponseDTOs
     * @param users list of user entities
     * @return list of UserResponseDTOs
     */
    public List<UserResponseDTO> toResponseDTOList(List<UserEntity> users) {
        if (users == null) {
            return null;
        }

        return users.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Convert User entity to basic UserResponseDTO (limited fields)
     * Useful for scenarios where you don't need all user details
     * @param user the user entity
     * @return basic UserResponseDTO
     */
    public UserResponseDTO toBasicResponseDTO(UserEntity user) {
        if (user == null) {
            return null;
        }

        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setId(user.getId());
        responseDTO.setUsername(user.getUsername());
        responseDTO.setFirstName(user.getFirstName());
        responseDTO.setLastName(user.getLastName());
        responseDTO.setRole(user.getRole());
        responseDTO.setIsActive(user.getIsActive());

        return responseDTO;
    }

    /**
     * Update User entity with data from UserRegistrationDTO
     * @param user existing user entity
     * @param registrationDTO registration DTO with new data
     */
    public void updateEntityFromRegistrationDTO(UserEntity user, UserRegistrationDTO registrationDTO) {
        if (user == null || registrationDTO == null) {
            return;
        }

        user.setUsername(registrationDTO.getUsername());
        user.setEmail(registrationDTO.getEmail());
        user.setFirstName(registrationDTO.getFirstName());
        user.setLastName(registrationDTO.getLastName());
        user.setPhoneNumber(registrationDTO.getPhoneNumber());
    }
}
