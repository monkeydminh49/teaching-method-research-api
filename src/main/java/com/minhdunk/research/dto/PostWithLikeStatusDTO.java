package com.minhdunk.research.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.minhdunk.research.entity.Assignment;
import com.minhdunk.research.entity.Post;
import com.minhdunk.research.entity.User;
import com.minhdunk.research.mapper.PostMapper;
import com.minhdunk.research.mapper.PostMapperImpl;
import com.minhdunk.research.utils.PostOrientation;
import com.minhdunk.research.utils.PostType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

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
    public LocalDateTime getPostTime(){
        return this.postTime.truncatedTo(ChronoUnit.SECONDS);
    }


    public PostWithLikeStatusDTO(Post post, User user) {
        PostMapperImpl postMapper = new PostMapperImpl();
        PostOutputDTO postOutputDTO =  postMapper.getPostOutputDTOFromPost(post);
        this.id = postOutputDTO.getId();
        this.authorId = postOutputDTO.getAuthorId();
        this.assignmentId = postOutputDTO.getAssignmentId();
        this.title = postOutputDTO.getTitle();
        this.caption = postOutputDTO.getCaption();
        this.orientation = postOutputDTO.getOrientation();
        this.type = postOutputDTO.getType();
        this.medias = postOutputDTO.getMedias();
        this.postTime = postOutputDTO.getPostTime();
        this.numberOfLikes = postOutputDTO.getNumberOfLikes();
        this.isLiked = user != null;
    }
}
