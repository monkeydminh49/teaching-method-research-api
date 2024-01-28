package com.minhdunk.research.repository;

import com.minhdunk.research.entity.Document;
import com.minhdunk.research.utils.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findAllByType(DocumentType type);
}
