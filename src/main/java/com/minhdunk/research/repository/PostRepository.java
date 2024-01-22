package com.minhdunk.research.repository;

import com.minhdunk.research.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p JOIN FETCH p.medias WHERE p.assignment.classroom.id = ?1")
    List<Post> getPostsByClassroomId(Long id);

    @Query("SELECT p FROM Post p WHERE p.assignment.classroom.id = ?1")
    List<Post> getPostsByClassroomIdWithoutMedias(Long id);

    @Query("SELECT p FROM Post p JOIN FETCH p.medias WHERE p.id = ?1")
    Optional<Post> findByIdWithMedias(Long postId);

    @Query("SELECT p FROM Post p JOIN FETCH p.medias WHERE p.assignment.classroom.id = ?1 AND p.authorId = ?2")
    List<Post> getPostsByClassroomIdAndAuthorId(Long id, Long userId);
}
