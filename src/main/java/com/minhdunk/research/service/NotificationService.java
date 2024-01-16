package com.minhdunk.research.service;

import com.minhdunk.research.dto.NotificationInputDTO;
import com.minhdunk.research.entity.Notification;
import com.minhdunk.research.entity.User;
import com.minhdunk.research.exception.NotFoundException;
import com.minhdunk.research.mapper.NotificationMapper;
import com.minhdunk.research.repository.ClassroomRepository;
import com.minhdunk.research.repository.NotificationRepository;
import com.minhdunk.research.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private ClassroomRepository classroomRepository;
    @Autowired
    private UserRepository userRepository;

    public void postNotificationToClassWithId(Principal principal, Long id, NotificationInputDTO request) {
        Notification notification = notificationMapper.getNotificationFromNotificationInputDTO(request);
        notification.setClassroom(classroomRepository.findById(id).orElseThrow(()-> new NotFoundException("Class id not found")));
        User author = userRepository.findByUsername(principal.getName()).get();
        notification.setPostTime(LocalDateTime.now());
        notification.setAuthor(author);
        notificationRepository.save(notification);
    }

    public List<Notification> getAllClassNotificationsByClassId(Long id) {
        return notificationRepository.findAllByClassroomId(id);
    }
}
