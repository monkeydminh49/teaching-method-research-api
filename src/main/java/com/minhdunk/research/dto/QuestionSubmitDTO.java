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
public class QuestionSubmitDTO {
    private Long questionId;
    private List<ChoiceSubmitDTO> choices;
}
