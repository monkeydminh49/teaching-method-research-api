package com.minhdunk.research.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class TestWithoutAnswerDTO {
    private Long id;
    private Long authorId;
    private Long documentId;
    private String title;
    private List<QuestionWithoutAnswerDTO> questions;
}
