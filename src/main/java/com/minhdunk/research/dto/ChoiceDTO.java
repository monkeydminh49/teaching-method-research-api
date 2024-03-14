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
public class ChoiceDTO {
    private Long id;
    private String content;
    private Boolean isAnswer;

    public Boolean getIsAnswer() {
        return isAnswer != null ? isAnswer : false;
    }
}
