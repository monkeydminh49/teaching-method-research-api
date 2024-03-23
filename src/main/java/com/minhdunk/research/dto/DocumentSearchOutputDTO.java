package com.minhdunk.research.dto;

import com.minhdunk.research.utils.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentSearchOutputDTO {
    private Long id;
    private String title;
    private String veryFirstText;
    private LocalDateTime postTime;
    private DocumentType type;
    private MediaOutputDTO thumbnail;
    private Integer numberOfLikes;
    private String topic;
}
