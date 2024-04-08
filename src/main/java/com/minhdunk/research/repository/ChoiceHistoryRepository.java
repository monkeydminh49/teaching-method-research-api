package com.minhdunk.research.repository;

import com.minhdunk.research.entity.ChoiceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChoiceHistoryRepository extends JpaRepository<ChoiceHistory, Long> {
}
