package com.minhdunk.research.service;

import com.minhdunk.research.entity.Document;
import com.minhdunk.research.entity.Media;
import com.minhdunk.research.entity.User;
import com.minhdunk.research.exception.NotFoundException;
import com.minhdunk.research.repository.DocumentRepository;
import com.minhdunk.research.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MediaService mediaService;
    @Autowired
    private DocumentRepository documentRepository;
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

    public List<Document> getFavouriteDocuments(Long id) {
        return documentRepository.findAllByLikedByUsersId(id);
    }

    public List<Document> getFavouriteDocuments(Long id, Integer page, Integer size) {
        return documentRepository.findAllByLikedByUsersId(id,  PageRequest.of(page, size));
    }
}
