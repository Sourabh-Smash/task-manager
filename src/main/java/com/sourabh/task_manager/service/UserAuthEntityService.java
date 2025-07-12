package com.sourabh.task_manager.service;

import com.sourabh.task_manager.entity.UserAuthEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface UserAuthEntityService {

    UserDetails save(UserAuthEntity userAuth);

    UserDetails loadUserByUsername(String username);
}
