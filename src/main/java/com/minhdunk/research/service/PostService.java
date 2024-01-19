package com.minhdunk.research.service;

import com.minhdunk.research.dto.PostInputDTO;
import com.minhdunk.research.entity.Assignment;
import com.minhdunk.research.entity.Media;
import com.minhdunk.research.entity.Post;
import com.minhdunk.research.entity.User;
import com.minhdunk.research.repository.UserRepository;
import com.minhdunk.research.utils.PostType;
import com.minhdunk.research.repository.AssignmentRepository;
import com.minhdunk.research.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;
import java.util.Set;

@Service
public class PostService {

    @Autowired
    private MediaService mediaService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private AssignmentRepository assignmentRepository;
    @Autowired
    private UserService userService;
    public Post submitAssignment(Principal principal, Long assignmentId, PostInputDTO request, Set<Media> medias) {
        User user = userService.getUserByUsername(principal.getName());
        Assignment assignment = assignmentRepository.findById(assignmentId).get();
        Post post = Post.builder()
                .authorId(user.getId())
                .caption(request.getCaption())
                .assignment(assignment)
                .type(PostType.PENDING)
                .orientation(request.getOrientation())
                .medias(medias)
                .build();
        return postRepository.save(post);
    }
}
