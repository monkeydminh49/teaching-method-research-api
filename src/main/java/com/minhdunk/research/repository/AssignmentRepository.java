package com.minhdunk.research.repository;

import com.minhdunk.research.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    @Query("SELECT a FROM Assignment a JOIN a.classroom c WHERE c.id = ?1")
    List<Assignment> findAllByClassroomId(Long id);
}
