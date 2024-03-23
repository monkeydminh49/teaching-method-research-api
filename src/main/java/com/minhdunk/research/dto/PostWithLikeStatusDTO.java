package com.minhdunk.research.dto;

import com.minhdunk.research.entity.Assignment;
import com.minhdunk.research.entity.Media;
import com.minhdunk.research.entity.Post;
import com.minhdunk.research.entity.User;
import com.minhdunk.research.utils.PostOrientation;
import com.minhdunk.research.utils.PostType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostWithLikeStatusDTO {

    private Long id;
    private Long authorId;
    private Long assignmentId;
    private String title;
    private String caption;
    private PostOrientation orientation;
    private PostType type;
    private List<MediaOutputDTO> medias;
    private LocalDateTime postTime;
    private Integer numberOfLikes;
    private Boolean isLiked;
    private Long submitterId;
    private String teacherComment;
    public LocalDateTime getPostTime(){
        return this.postTime.truncatedTo(ChronoUnit.SECONDS);
    }


    public PostWithLikeStatusDTO(Post post, User user) {
        this.id = post.getId();
        this.authorId = post.getAuthorId();
        this.assignmentId = postAssignmentId(post);
        this.title = post.getTitle();
        this.caption = post.getCaption();
        this.orientation = post.getOrientation();
        this.type = post.getType();
        this.medias = mediaSetToMediaOutputDTOList(post.getMedias());
        this.postTime = post.getPostTime();
        this.numberOfLikes = post.getNumberOfLikes();
        this.isLiked = user != null;
        this.teacherComment = post.getTeacherComment();
    }

    private Long postAssignmentId(Post post) {
        if ( post == null ) {
            return null;
        }
        Assignment assignment = post.getAssignment();
        if ( assignment == null ) {
            return null;
        }
        Long id = assignment.getId();
        if ( id == null ) {
            return null;
        }
        return id;
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

    protected List<MediaOutputDTO> mediaSetToMediaOutputDTOList(Set<Media> set) {
        if ( set == null ) {
            return null;
        }

        List<MediaOutputDTO> list = new ArrayList<MediaOutputDTO>( set.size() );
        for ( Media media : set ) {
            list.add( mediaToMediaOutputDTO( media ) );
        }

        return list;
    }
}
