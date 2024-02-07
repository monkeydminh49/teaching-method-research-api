package com.minhdunk.research.service;

import com.minhdunk.research.dto.CommentInputDTO;
import com.minhdunk.research.dto.CommentOutputDTO;
import com.minhdunk.research.entity.Comment;
import com.minhdunk.research.entity.Notification;
import com.minhdunk.research.entity.Post;
import com.minhdunk.research.entity.User;
import com.minhdunk.research.mapper.CommentMapper;
import com.minhdunk.research.repository.CommentRepository;
import com.minhdunk.research.utils.CommentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private NotificationService notificationService;

    public Comment postCommentToPostId(Principal principal, Long postId, CommentInputDTO request) {
        User user = userService.getUserByUsername(principal.getName());
        Post post = postService.getPostById(postId);

        Comment comment = commentMapper.getCommentFromCommentInputDto(request);

        comment.setDestinationId(post.getId());
        comment.setUser(user);
        ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
        comment.setPostTime(LocalDateTime.now(zoneId));
        comment.setType(CommentType.COMMENT_POST);

        return commentRepository.save(comment);
    }

    public CommentOutputDTO getCommentOutputDtoById(Long commentId) {
        return commentRepository.getCommentOutputDtoById(commentId);
    }

    public List<Comment> getAllCommentByPostId(Long postId) {
        return commentRepository.findAllCommentsByPostId(postId);
    }

    public Comment postCommentToNotificationId(Principal principal, Long notificationId, CommentInputDTO request) {
        User user = userService.getUserByUsername(principal.getName());
        Comment newComment = commentMapper.getCommentFromCommentInputDto(request);
        Notification notification = notificationService.getNotificationById(notificationId);
        newComment.setDestinationId(notification.getId());
        newComment.setUser(user);
        ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
        newComment.setPostTime(LocalDateTime.now(zoneId));
        newComment.setType(CommentType.COMMENT_NOTIFICATION);

        return commentRepository.save(newComment);
    }

    public List<Comment> getAllCommentByNotificationId(Long notificationId) {
        return commentRepository.findAllCommentsByNotificationId(notificationId);
    }
}
