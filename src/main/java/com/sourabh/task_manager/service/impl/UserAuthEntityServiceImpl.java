package com.sourabh.task_manager.service.impl;

import com.sourabh.task_manager.entity.UserAuthEntity;
import com.sourabh.task_manager.repository.UserAuthEntityRepository;
import com.sourabh.task_manager.service.UserAuthEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAuthEntityServiceImpl implements UserAuthEntityService, UserDetailsService {

    @Autowired
    private UserAuthEntityRepository userAuthEntityRepository;

    @Override
    public UserDetails save(UserAuthEntity userAuth){
        return userAuthEntityRepository.save(userAuth);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userAuthEntityRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}