package com.minhdunk.research.repository;

import com.minhdunk.research.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("Select n from Notification n Join FETCH n.author WHERE n.classroom.id = ?1")
    List<Notification> findAllByClassroomId(Long id);
}
