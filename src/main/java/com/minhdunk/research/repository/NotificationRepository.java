package com.minhdunk.research.repository;

import com.minhdunk.research.dto.NotificationOutputDTO;
import com.minhdunk.research.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query(value = "SELECT new com.minhdunk.research.dto.NotificationOutputDTO(n.id, n.author.id, n.author.firstName, n.author.lastName, n.author.avatar.id, n.content, n.postTime, c) " +
            "FROM Notification n " +
            "LEFT JOIN Comment c " +
            "ON n.id = c.destinationId " +
            "AND c.type = 'COMMENT_NOTIFICATION' " +
            "AND n.classroom.id = ?1 " +
            "AND c.postTime in (SELECT MAX(c2.postTime) FROM Comment c2  where c2.type = 'COMMENT_NOTIFICATION' group by c2.destinationId) ")
    List<NotificationOutputDTO> findAllByClassroomId(Long id);
}
