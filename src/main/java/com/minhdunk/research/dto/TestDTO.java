package com.minhdunk.research.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestDTO {
    private Long id;
    private Long authorId;
    private Long documentId;
    private String title;
    private List<QuestionDTO> questions;
    public String type;

    public List<QuestionDTO> getQuestions() {
        return questions != null ? questions : List.of();
    }
}
