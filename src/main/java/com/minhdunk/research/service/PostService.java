package com.minhdunk.research.service;

import com.minhdunk.research.component.UserInfoUserDetails;
import com.minhdunk.research.dto.PostInputDTO;
import com.minhdunk.research.dto.PostWithLikeStatusDTO;
import com.minhdunk.research.entity.*;
import com.minhdunk.research.exception.ForbiddenException;
import com.minhdunk.research.exception.NotFoundException;
import com.minhdunk.research.repository.AssignmentRepository;
import com.minhdunk.research.repository.CounsellingRepository;
import com.minhdunk.research.repository.GroupRepository;
import com.minhdunk.research.repository.PostRepository;
import com.minhdunk.research.utils.PostAction;
import com.minhdunk.research.utils.PostOrientation;
import com.minhdunk.research.utils.PostType;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private AssignmentRepository assignmentRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private ClassroomService classroomService;
    @Autowired
    private CounsellingRepository counsellingRepository;

    @Transactional
    public Post submitAssignment(Principal principal, Long assignmentId, PostInputDTO request, Set<Media> medias, Long[] memberIds) {
        User user = userService.getUserByUsername(principal.getName());
        Counselling counselling = null;
        if (request.getCounsellingId() != null){
            counselling = counsellingRepository.findById(request.getCounsellingId()).orElseThrow(() -> new NotFoundException("Counselling with id " + assignmentId + " not found"));
        }
        Assignment assignment = assignmentRepository.findById(assignmentId).get();
        Set<User> members = new HashSet<>();
        Group group = new Group();

        Post post = Post.builder()
                .title(request.getTitle())
                .caption(request.getCaption())
                .assignment(assignment)
                .type(PostType.PENDING)
                .orientation(request.getOrientation())
                .postTime(LocalDateTime.now())
                .numberOfLikes(0)
                .medias(medias)
                .submitter(user)
                .counselling(counselling)
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

    public List<Post> getPostsByClassroomId(Long id, PostType type) {
        if (type == null) {
            return getPostsByClassroomId(id);
        }
        return postRepository.getPostsByClassroomId(id, type);
    }

    public List<Post> getPostsByClassroomIdWithoutMedias(Long id) {
        return postRepository.getPostsByClassroomIdWithoutMedias(id);
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

    public void likePost(Long id, Principal principal) {
        Post post = getPostById(id);
        User user = userService.getUserByUsername(principal.getName());
        post.addLikedUser(user);
        postRepository.save(post);
    }

    public void unlikePost(Long id, Principal principal) {
        Post post = getPostById(id);
        User user = userService.getUserByUsername(principal.getName());
        post.removeLikedUser(user);
        postRepository.save(post);
    }

    public PostWithLikeStatusDTO getPostByIdWithLikeStatus(Long postId, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        return postRepository.findByIdWithLikeStatus(postId, user.getId())
                .orElseThrow(() -> new NotFoundException("Post with id " + postId + " not found"));
    }

    public List<PostWithLikeStatusDTO> getPostsByClassroomIdWithLikeStatus(Long classId, PostType type, PostOrientation orientation, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        return postRepository.findByClassroomIdWithLikeStatus(classId, type, orientation, user.getId());
    }

    public List<Post> getPostsByAssignmentId(Long id, Authentication authentication) {
        UserInfoUserDetails userDetails = (UserInfoUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        List<Post> posts =  postRepository.getPostsByAssignmentIdNotForGroup(id, user.getId());
        List<Post> groupPosts = postRepository.getPostsByAssignmentIdForGroup(id, user.getId());
        posts.addAll(groupPosts);
        return posts;
    }

    public void addTeacherComment(Authentication authentication, Long postId, String comment) {
        UserInfoUserDetails userDetails = (UserInfoUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        Post post = getPostById(postId);
        // TODO: Resolve n+1 problem
        Classroom classroom = classroomService.getClassroomById(post.getAssignment().getClassroom().getId());
        if (!user.getId().equals(classroom.getTeacher().getId())) {
            throw new ForbiddenException("You are not allowed to perform this action");
        }
        post.setTeacherComment(comment);
        postRepository.save(post);
    }
}
