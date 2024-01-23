package com.minhdunk.research.service;

import com.minhdunk.research.entity.Media;
import com.minhdunk.research.entity.User;
import com.minhdunk.research.exception.NotFoundException;
import com.minhdunk.research.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MediaService mediaService;
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(()->new NotFoundException("User with username " + username + " not found."));
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()->new NotFoundException("User with id " + id + " not found."));
    }

    public User updateAvatar(Principal principal, MultipartFile avatar) throws IOException {
        User user = getUserByUsername(principal.getName());
        if (user.getAvatar() != null) {
            Media avatarMedia = user.getAvatar();
            user.setAvatar(null);
            mediaService.deleteMedia(avatarMedia.getId());
        }

        user.setAvatar(mediaService.persistMedia(avatar));
        return userRepository.save(user);
    }
}
