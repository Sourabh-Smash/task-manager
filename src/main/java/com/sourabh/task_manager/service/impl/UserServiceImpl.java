package com.sourabh.task_manager.service.impl;

import com.sourabh.task_manager.dto.request.UserLoginDTO;
import com.sourabh.task_manager.dto.request.UserRegistrationDTO;
import com.sourabh.task_manager.dto.request.UserUpdateDTO;
import com.sourabh.task_manager.dto.response.UserResponseDTO;
import com.sourabh.task_manager.enums.UserRole;
import com.sourabh.task_manager.entity.UserEntity;
import com.sourabh.task_manager.util.DuplicateResourceException;
import com.sourabh.task_manager.util.ResourceNotFoundException;
import com.sourabh.task_manager.mapper.UserMapper;
import com.sourabh.task_manager.repository.UserRepository;
import com.sourabh.task_manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service implementation for User-related business logic
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponseDTO registerUser(UserRegistrationDTO registrationDTO) {
        // Check if username already exists
        if (userRepository.existsByUsername(registrationDTO.getUsername())) {
            throw new DuplicateResourceException("Username already exists: " + registrationDTO.getUsername());
        }

        // Check if email already exists
        if (userRepository.existsByEmail(registrationDTO.getEmail())) {
            throw new DuplicateResourceException("Email already exists: " + registrationDTO.getEmail());
        }

        // Create new user entity
        UserEntity user = userMapper.toEntity(registrationDTO);

        // Encode password
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));

        // Set default role
        user.setRole(UserRole.USER);

        // Save user
        UserEntity savedUser = userRepository.save(user);

        return userMapper.toResponseDTO(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return userMapper.toResponseDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUserByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        return userMapper.toResponseDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUserByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        return userMapper.toResponseDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponseDTO> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getActiveUsers() {
        return userRepository.findByIsActiveTrue().stream()
                .map(userMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getUsersByRole(UserRole role) {
        return userRepository.findByRole(role).stream()
                .map(userMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponseDTO> searchUsers(String searchTerm, Pageable pageable) {
        return userRepository.searchUsersByName(searchTerm, pageable)
                .map(userMapper::toResponseDTO);
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserUpdateDTO updateDTO) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        // Check if email is being changed and if it already exists
        if (updateDTO.getEmail() != null && !updateDTO.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(updateDTO.getEmail())) {
                throw new DuplicateResourceException("Email already exists: " + updateDTO.getEmail());
            }
            user.setEmail(updateDTO.getEmail());
            user.setIsEmailVerified(false); // Reset email verification if email changed
        }

        // Update other fields
        if (updateDTO.getFirstName() != null) {
            user.setFirstName(updateDTO.getFirstName());
        }
        if (updateDTO.getLastName() != null) {
            user.setLastName(updateDTO.getLastName());
        }
        if (updateDTO.getPhoneNumber() != null) {
            user.setPhoneNumber(updateDTO.getPhoneNumber());
        }

        UserEntity updatedUser = userRepository.save(user);
        return userMapper.toResponseDTO(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserResponseDTO activateUser(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        user.activate();
        UserEntity updatedUser = userRepository.save(user);
        return userMapper.toResponseDTO(updatedUser);
    }

    @Override
    public UserResponseDTO deactivateUser(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        user.deactivate();
        UserEntity updatedUser = userRepository.save(user);
        return userMapper.toResponseDTO(updatedUser);
    }

    @Override
    public UserResponseDTO verifyEmail(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        user.verifyEmail();
        UserEntity updatedUser = userRepository.save(user);
        return userMapper.toResponseDTO(updatedUser);
    }

    @Override
    public UserResponseDTO updateUserRole(Long id, UserRole role) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        user.setRole(role);
        UserEntity updatedUser = userRepository.save(user);
        return userMapper.toResponseDTO(updatedUser);
    }

    @Override
    public void updateLastLogin(Long id) {
        userRepository.updateLastLogin(id, LocalDateTime.now());
    }

    @Override
    public boolean changePassword(Long id, String currentPassword, String newPassword) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        // Verify current password
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            return false;
        }

        // Update password
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean validateLogin(UserLoginDTO loginDTO) {
        Optional<UserEntity> userOptional = userRepository.findByUsernameOrEmail(
                loginDTO.getUsernameOrEmail(), loginDTO.getUsernameOrEmail());

        if (userOptional.isEmpty()) {
            return false;
        }

        UserEntity user = userOptional.get();

        // Check if user is active
        if (!user.getIsActive()) {
            return false;
        }

        // Verify password
        return passwordEncoder.matches(loginDTO.getPassword(), user.getPassword());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isUsernameAvailable(String username) {
        return !userRepository.existsByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isEmailAvailable(String email) {
        return !userRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public long getTotalUserCount() {
        return userRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public long getActiveUserCount() {
        return userRepository.countByIsActiveTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public long getUserCountByRole(UserRole role) {
        return userRepository.countByRole(role);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getInactiveUsers(int daysSinceLastLogin) {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(daysSinceLastLogin);
        return userRepository.findInactiveUsersSince(cutoffDate).stream()
                .map(userMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
