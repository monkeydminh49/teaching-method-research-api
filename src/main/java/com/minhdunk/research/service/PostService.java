package com.minhdunk.research.service;

import com.minhdunk.research.dto.PostInputDTO;
import com.minhdunk.research.entity.*;
import com.minhdunk.research.exception.ForbiddenException;
import com.minhdunk.research.exception.NotFoundException;
import com.minhdunk.research.mapper.PostMapper;
import com.minhdunk.research.repository.GroupRepository;
import com.minhdunk.research.utils.PostAction;
import com.minhdunk.research.utils.PostType;
import com.minhdunk.research.repository.AssignmentRepository;
import com.minhdunk.research.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
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
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private ClassroomService classroomService;


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

        if (assignment.getIsForGroup()) {
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

    public List<Post> getPostsByClassroomId(Long id) {
        return postRepository.getPostsByClassroomId(id);
    }

    public Post getPostByIdWithMedias(Long postId) {
        return postRepository.findByIdWithMedias(postId).orElseThrow(() -> new NotFoundException("Post with id " + postId + " not found"));
    }

    public Post getPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Post with id " + postId + " not found"));
    }

    public List<Post> getPostsByClassroomIdAndAuthorId(Long id, Long authorId) {
        return postRepository.getPostsByClassroomIdAndAuthorId(id, authorId);
    }

    public Post updatePostType(Principal principal, Long postId, PostAction action) {
        User user = userService.getUserByUsername(principal.getName());
        Post post = getPostByIdWithMedias(postId);
        Classroom classroom = classroomService.getClassroomById(post.getAssignment().getClassroom().getId());

        if (!user.getId().equals(classroom.getTeacher().getId())) {
            throw new ForbiddenException("You are not allowed to perform this action");
        }

        if (action.equals(PostAction.APPROVE)) {
            log.info("Approve post id " + postId);
            post.setType(PostType.APPROVED);
        } else if (action.equals(PostAction.REJECT)) {
            log.info("Reject post id " + postId);
            post.setType(PostType.REJECTED);
        }
        return postRepository.save(post);
    }

}
