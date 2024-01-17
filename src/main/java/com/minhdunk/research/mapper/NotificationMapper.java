package com.minhdunk.research.mapper;

import com.minhdunk.research.dto.NotificationInputDTO;
import com.minhdunk.research.dto.NotificationOutputDTO;
import com.minhdunk.research.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    @Mapping(target = "postTime", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "classroom", ignore = true)
    @Mapping(target = "author", ignore = true)
    Notification getNotificationFromNotificationInputDTO(NotificationInputDTO notificationInputDTO);

    @Mapping(target = "authorId", source = "author.id")
    NotificationOutputDTO getNotificationOutputDTOFromNotification(Notification notification);
    @Mapping(target = "authorId", source = "author.id")
    List<NotificationOutputDTO> getNotificationOutputDTOsFromNotifications(List<Notification> notifications);
}
