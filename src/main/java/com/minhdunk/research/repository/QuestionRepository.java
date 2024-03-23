package com.minhdunk.research.repository;

import com.minhdunk.research.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT q FROM Question q JOIN FETCH q.choices WHERE q.test.id = :testId ")
    List<Question> findByTestId(Long testId);
}
