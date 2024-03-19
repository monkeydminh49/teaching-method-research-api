package com.minhdunk.research.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class QuestionInputDTO {
    private String question;
    @Schema(description = "Type of the question", example = "SINGLE_CHOICE | FILL_IN_BLANK | MULTI_CHOICE")
    private String type;
    private List<ChoiceInputDTO> choices;
    private List<HintInputDTO> hints;
    private List<HintInputDTO> answerHints;
}
