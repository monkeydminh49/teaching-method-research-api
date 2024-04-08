package com.minhdunk.research.repository;

import com.minhdunk.research.entity.QuestionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionHistoryRepository extends JpaRepository<QuestionHistory, Long> {
}
