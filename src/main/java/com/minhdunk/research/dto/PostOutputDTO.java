package com.minhdunk.research.dto;

import com.minhdunk.research.entity.Assignment;
import com.minhdunk.research.utils.PostOrientation;
import com.minhdunk.research.utils.PostType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    public LocalDateTime getPostTime(){
        return this.postTime.truncatedTo(ChronoUnit.SECONDS);
    }

}
