package com.minhdunk.research.service;

import com.minhdunk.research.dto.PostInputDTO;
import com.minhdunk.research.entity.*;
import com.minhdunk.research.repository.GroupRepository;
import com.minhdunk.research.utils.PostType;
import com.minhdunk.research.repository.AssignmentRepository;
import com.minhdunk.research.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class PostService {

    @Autowired
    private MediaService mediaService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private AssignmentRepository assignmentRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private GroupRepository groupRepository;

    public Post submitAssignment(Principal principal, Long assignmentId, PostInputDTO request, Set<Media> medias, Long[] memberIds) {
        User user = userService.getUserByUsername(principal.getName());
        Assignment assignment = assignmentRepository.findById(assignmentId).get();
        Set<User> members = new HashSet<>();
        Group group = new Group();

        Post post = Post.builder()
                .caption(request.getCaption())
                .assignment(assignment)
                .type(PostType.PENDING)
                .orientation(request.getOrientation())
                .medias(medias)
                .build();

        if (assignment.getIsForGroup() ){
            members.add(user);
            if (memberIds != null) {
                for (Long memberId : memberIds) {
                    log.info("Member id: " + memberId);
                    if (!user.getId().equals(memberId)) {
                        User member = userService.getUserById(memberId);
                        members.add(member);
                    }
                }
            }
            group.setStudents(members);
            group.setAssignment(assignment);
            group = groupRepository.save(group);
            post.setAuthorId(group.getId());
        } else {
            post.setAuthorId(user.getId());
        }


        return postRepository.save(post);
    }
}
