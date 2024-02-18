package com.minhdunk.research.dto;

import com.minhdunk.research.entity.Media;
import com.minhdunk.research.entity.User;
import com.minhdunk.research.utils.DocumentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentOutputDTO {
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
}
