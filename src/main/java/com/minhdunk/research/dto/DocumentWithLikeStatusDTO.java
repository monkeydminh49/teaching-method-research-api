package com.minhdunk.research.dto;

import com.minhdunk.research.entity.Document;
import com.minhdunk.research.entity.Media;
import com.minhdunk.research.entity.User;
import com.minhdunk.research.mapper.DocumentMapper;
import com.minhdunk.research.mapper.DocumentMapperImpl;
import com.minhdunk.research.utils.DocumentTopic;
import com.minhdunk.research.utils.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentWithLikeStatusDTO {
    private Long id;
    private UserOutputDTO author;
    private String title;
    private String veryFirstText;
    private LocalDateTime postTime;
    private DocumentType type;
    private MediaOutputDTO audio;
    private String notionPageId;
    private MediaOutputDTO thumbnail;
    private Integer numberOfLikes;
    private String topic;
    private Boolean isLiked;
    public LocalDateTime getPostTime(){
        return this.postTime.truncatedTo(ChronoUnit.SECONDS);
    }

    public DocumentWithLikeStatusDTO(Document document, User user) {
        this.id = document.getId();
        this.author =  userToUserOutputDTO( document.getAuthor() );
        this.title = document.getTitle();
        this.veryFirstText =  document.getVeryFirstText();
        this.postTime = document.getPostTime();
        this.type = document.getType();
        this.audio = mediaToMediaOutputDTO( document.getAudio() ) ;
        this.notionPageId = document.getNotionPageId();
        this.thumbnail = mediaToMediaOutputDTO( document.getThumbnail() ) ;
        this.numberOfLikes = document.getNumberOfLikes();;
        this.topic = document.getTopic();
        this.isLiked = user != null;
    }

    protected UserOutputDTO userToUserOutputDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserOutputDTO.UserOutputDTOBuilder userOutputDTO = UserOutputDTO.builder();

        userOutputDTO.id( user.getId() );
        userOutputDTO.firstName( user.getFirstName() );
        userOutputDTO.lastName( user.getLastName() );
        userOutputDTO.username( user.getUsername() );
        userOutputDTO.role( user.getRole() );
        userOutputDTO.dateOfBirth( user.getDateOfBirth() );

        return userOutputDTO.build();
    }

    protected MediaOutputDTO mediaToMediaOutputDTO(Media media) {
        if ( media == null ) {
            return null;
        }

        MediaOutputDTO.MediaOutputDTOBuilder mediaOutputDTO = MediaOutputDTO.builder();

        mediaOutputDTO.id( media.getId() );
        mediaOutputDTO.name( media.getName() );
        mediaOutputDTO.description( media.getDescription() );
        mediaOutputDTO.type( media.getType() );
        mediaOutputDTO.size( media.getSize() );

        return mediaOutputDTO.build();
    }
}
