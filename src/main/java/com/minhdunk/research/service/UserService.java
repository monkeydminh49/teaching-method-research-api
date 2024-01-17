package com.minhdunk.research.service;

import com.minhdunk.research.entity.User;
import com.minhdunk.research.exception.NotFoundException;
import com.minhdunk.research.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(()->new NotFoundException("User with username " + username + " not found."));
    }
}
