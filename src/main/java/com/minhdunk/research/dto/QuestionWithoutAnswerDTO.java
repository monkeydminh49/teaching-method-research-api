package com.minhdunk.research.dto;


import com.minhdunk.research.utils.QuestionType;
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
public class QuestionWithoutAnswerDTO {
    private Long id;
    private QuestionType type;
    private String question;
    private List<ChoiceWithoutAnswerDTO> choices;
    private String hint;
}
