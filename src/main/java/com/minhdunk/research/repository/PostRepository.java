package com.minhdunk.research.repository;

import com.minhdunk.research.dto.PostWithLikeStatusDTO;
import com.minhdunk.research.entity.Post;
import com.minhdunk.research.utils.PostOrientation;
import com.minhdunk.research.utils.PostType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.medias WHERE p.assignment.classroom.id = ?1")
    List<Post> getPostsByClassroomId(Long id);

    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.medias WHERE p.assignment.classroom.id = ?1 AND p.type = ?2")
    List<Post> getPostsByClassroomId(Long id, PostType type);

    @Query("SELECT p FROM Post p WHERE p.assignment.classroom.id = ?1")
    List<Post> getPostsByClassroomIdWithoutMedias(Long id);

    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.medias WHERE p.id = ?1")
    Optional<Post> findByIdWithMedias(Long postId);


    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.medias WHERE p.assignment.classroom.id = ?1 AND p.authorId = ?2")
    List<Post> getPostsByClassroomIdAndAuthorId(Long id, Long userId);

    @Query("SELECT new com.minhdunk.research.dto.PostWithLikeStatusDTO(p, u) " +
            "FROM Post p " +
            "LEFT JOIN p.likedByUsers u " +
            "ON u.id = ?2 " +
            "WHERE p.id = ?1 ")
    Optional<PostWithLikeStatusDTO> findByIdWithLikeStatus(Long postId, Long userId);

    @Query("SELECT new com.minhdunk.research.dto.PostWithLikeStatusDTO(p, u) " +
            "FROM Post p " +
            "LEFT JOIN p.likedByUsers u " +
            "ON u.id = :userId " +
            "WHERE p.assignment.classroom.id = :classId " +
            "AND (:type is null OR p.type = :type) " +
            "AND (:orientation is null OR p.orientation = :orientation)")
    List<PostWithLikeStatusDTO> findByClassroomIdWithLikeStatus(Long classId, PostType type, PostOrientation orientation, Long userId);
}
