package com.minhdunk.research.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.minhdunk.research.entity.Comment;
import com.minhdunk.research.mapper.CommentMapper;
import com.minhdunk.research.mapper.CommentMapperImpl;
import com.minhdunk.research.mapper.NotificationMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationOutputDTO {
    private Long id;
    private Long authorId;
    private String avatarId;
    private String content;
    private LocalDateTime postTime;
    private CommentOutputDTO lastComment;
    @JsonIgnore
    private CommentMapperImpl commentMapper;

    public NotificationOutputDTO(Long id, Long authorId, String avatarId ,String content, LocalDateTime postTime, Comment lastComment) {
        this.commentMapper = new CommentMapperImpl();
        this.id = id;
        this.avatarId = avatarId;
        this.authorId = authorId;
        this.content = content;
        this.postTime = postTime;
        this.lastComment = commentMapper.getCommentOutputDtoFromComment(lastComment);
    }

    public LocalDateTime getPostTime(){
        return this.postTime.truncatedTo(ChronoUnit.SECONDS);
    }
}
