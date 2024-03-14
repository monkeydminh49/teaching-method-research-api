package com.minhdunk.research.repository;

import com.minhdunk.research.entity.Test;
import com.minhdunk.research.utils.TestType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    @Query("SELECT t FROM Test t WHERE t.document.id = :documentId AND (:type IS NULL OR t.type = :type)")
    Optional<Test> findByDocumentIdAndType(Long documentId, TestType type);

    @Query("SELECT t FROM Test t WHERE t.document.id = :documentId AND t.type = :type")
    Optional<Test> findByDocumentIdAndType(Long documentId, String type);
}
