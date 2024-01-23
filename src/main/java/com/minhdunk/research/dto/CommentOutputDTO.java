package com.minhdunk.research.dto;

import com.minhdunk.research.utils.CommentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentOutputDTO {
    private Long id;
    private Long userId;
    private String userFirstName;
    private String userLastName;
    private String userAvatarId;
    private Long destinationId;
    private String content;
    private LocalDateTime postTime;
    private CommentType type;


    public LocalDateTime getPostTime(){
        return this.postTime.truncatedTo(ChronoUnit.SECONDS);
    }
}
