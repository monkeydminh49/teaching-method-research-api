package com.minhdunk.research.repository;

import com.minhdunk.research.entity.Counselling;
import com.minhdunk.research.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CounsellingRepository extends JpaRepository<Counselling, Long> {
    @Query("SELECT c FROM Counselling c WHERE c.document.id = :documentId")
    List<Counselling> findAllByDocumentId(Long documentId);
}
