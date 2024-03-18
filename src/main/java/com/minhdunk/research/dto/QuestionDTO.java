package com.minhdunk.research.dto;

import com.minhdunk.research.utils.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionDTO {
    private Long id;
    private QuestionType type;
    private String question;
    private List<ChoiceDTO> choices;
    private String hint;
    private String answerHint;

    public List<ChoiceDTO> getChoices() {
        return choices != null ? choices : List.of();
    }

    public QuestionType getType() {
        return type != null ? type : QuestionType.SINGLE_CHOICE;
    }
}

