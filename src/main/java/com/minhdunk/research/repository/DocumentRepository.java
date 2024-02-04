package com.minhdunk.research.repository;

import com.minhdunk.research.dto.DocumentWithLikeStatusDTO;
import com.minhdunk.research.entity.Document;
import com.minhdunk.research.utils.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findAllByType(DocumentType type);

    @Query("select d from Document d join d.likedByUsers u where u.id = ?1")
    List<Document> findAllByLikedByUsersId(Long id);


    @Query("SELECT new com.minhdunk.research.dto.DocumentWithLikeStatusDTO(d, u) FROM Document d " +
            "LEFT JOIN d.likedByUsers u " +
            "ON u.id = ?2 " +
            "WHERE d.id = ?1"
    )
    Optional<DocumentWithLikeStatusDTO> getDocumentWithLikeStatus(Long id, Long userId);

    @Query("SELECT new com.minhdunk.research.dto.DocumentWithLikeStatusDTO(d, u) FROM Document d " +
            "LEFT JOIN d.likedByUsers u " +
            "ON u.id = ?2 " +
            "WHERE d.type = ?1"
    )
    List<DocumentWithLikeStatusDTO> getDocumentsWithLikeStatusByType(DocumentType type, Long userId);
}
