package com.minhdunk.research.repository;

import com.minhdunk.research.dto.CommentOutputDTO;
import com.minhdunk.research.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT new com.minhdunk.research.dto.CommentOutputDTO(c.id, c.user.id, c.user.firstName, c.user.lastName ,c.user.avatar.id, c.destinationId, c.content, c.postTime, c.type) " +
            "FROM Comment c " +
            "WHERE c.id = ?1")
    CommentOutputDTO getCommentOutputDtoById(Long commentId);

    @Query("SELECT c " +
            "FROM Comment c " +
            "JOIN FETCH c.user " +
            "JOIN Post p " +
            "ON c.destinationId = p.id " +
            "WHERE p.id = ?1 " +
            "AND c.type = 'COMMENT_POST'")
    List<Comment> findAllCommentsByPostId(Long postId);

    @Query("SELECT c " +
            "FROM Comment c " +
            "JOIN FETCH c.user " +
            "JOIN Notification n " +
            "ON c.destinationId = n.id " +
            "WHERE n.id = ?1 " +
            "AND c.type = 'COMMENT_NOTIFICATION'")
    List<Comment> findAllCommentsByNotificationId(Long notificationId);
}
