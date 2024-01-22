package com.minhdunk.research.repository;

import com.minhdunk.research.dto.UserOutputDTO;
import com.minhdunk.research.entity.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
       Optional<Classroom> findByCode(String code);

       @Query("SELECT c FROM Classroom c JOIN c.students s WHERE s.id = ?1")
       List<Classroom> getListClassroomsByStudentId(Long id);

       List<Classroom> findAllByTeacherId(Long id);

}
