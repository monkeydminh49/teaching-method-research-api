package com.minhdunk.research.repository;

import com.minhdunk.research.dto.DocumentWithLikeStatusDTO;
import com.minhdunk.research.entity.Document;
import com.minhdunk.research.utils.DocumentTopic;
import com.minhdunk.research.utils.DocumentType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findAllByType(DocumentType type);

    @Query("select d from Document d join d.likedByUsers du where du.user.id = ?1")
    List<Document> findAllByLikedByUsersId(Long id);

    @Query("select d from Document d join d.likedByUsers du where du.user.id = ?1 order by du.likedAt desc")
    List<Document> findAllByLikedByUsersId(Long id, Pageable pageable);


    @Query("SELECT new com.minhdunk.research.dto.DocumentWithLikeStatusDTO(d, du.user) FROM Document d " +
            "LEFT JOIN d.likedByUsers du " +
            "ON du.user.id = :userId " +
            "WHERE d.id = :id " +
            "ORDER BY d.postTime DESC"
    )
    Optional<DocumentWithLikeStatusDTO> getDocumentWithLikeStatus(Long id, Long userId);

    @Query("SELECT new com.minhdunk.research.dto.DocumentWithLikeStatusDTO(d, du.user) FROM Document d " +
            "LEFT JOIN d.likedByUsers du " +
            "WHERE (:type IS NULL OR d.type = :type)"
    )
    List<DocumentWithLikeStatusDTO> getDocumentsWithLikeStatusByType(DocumentType type);

    @Query("SELECT new com.minhdunk.research.dto.DocumentWithLikeStatusDTO(d, du.user) FROM Document d " +
            "LEFT JOIN d.likedByUsers du " +
            "ON du.user.id = :userId " +
            "AND (:type IS NULL OR d.type = :type) " +
            "AND (:topic IS NULL OR d.topic = :topic)" +
            "ORDER BY d.postTime DESC"
    )
    List<DocumentWithLikeStatusDTO> getDocumentsWithLikeStatusByType(DocumentType type, DocumentTopic topic, Long userId);

    @Query("SELECT d FROM Document d WHERE (:type is null or d.type=:type) AND (:topic is null or d.topic=:topic)")
    List<Document> findAllByTypeAndByTopic(DocumentType type, String topic);

    @Query("SELECT d FROM Document d WHERE (:type is null or d.type=:type) AND (:topic is null or d.topic=:topic)")
    List<Document> findAllByTypeAndByTopic(DocumentType type, DocumentTopic topic);
}
