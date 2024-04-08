package com.minhdunk.research.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class ChoiceHistoryOutputDTO {
    private Long id;
    private String content;
    private Boolean isAnswer;
    private Boolean isPicked;
    private Long questionId;
    private Long testId;
}
