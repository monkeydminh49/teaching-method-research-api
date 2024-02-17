package com.minhdunk.research.repository;

import com.minhdunk.research.dto.AssignmentOutputDTO;
import com.minhdunk.research.dto.AssignmentStatusOutputDTO;
import com.minhdunk.research.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    @Query("SELECT a FROM Assignment a JOIN a.classroom c WHERE c.id = ?1")
    List<Assignment> findAllByClassroomId(Long id);

    @Query("SELECT new com.minhdunk.research.dto.AssignmentStatusOutputDTO(a, p) " +
            "FROM Assignment a " +
            "LEFT JOIN Post p " +
            "ON a.id = p.assignment.id " +
            "AND " +
            "   ((p.authorId = ?2 AND a.isForGroup = false) " +
            "       OR " +
            "   (a.isForGroup = true " +
            "       AND EXISTS " +
            "           (SELECT g FROM Group g JOIN g.students gs WHERE g.id = p.authorId AND gs.id = ?2)))" +
            "WHERE a.classroom.id = ?1"
    )
    List<AssignmentStatusOutputDTO> findAllAssignmentsStatusByClassroomId(Long id, Long userId);
}
