package com.sourabh.task_manager.repository;

import com.sourabh.task_manager.entity.UserAuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAuthEntityRepository extends JpaRepository<UserAuthEntity, Long> {
    Optional<UserAuthEntity> findByUsername(String username);
}
