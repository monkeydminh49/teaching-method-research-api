package com.minhdunk.research.dto;

import com.minhdunk.research.entity.QuestionHistory;
import com.minhdunk.research.utils.HintType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HintHistoryOutputDTO {
    private Long id;
    private String content;
    private HintType type;
}
