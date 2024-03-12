package com.minhdunk.research.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.minhdunk.research.entity.Assignment;
import com.minhdunk.research.entity.Post;
import com.minhdunk.research.mapper.PostMapper;
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
public class PostOutputDTO {
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
    private Long submitterId;
    private String teacherComment;
    public LocalDateTime getPostTime(){
        return this.postTime.truncatedTo(ChronoUnit.SECONDS);
    }


}
