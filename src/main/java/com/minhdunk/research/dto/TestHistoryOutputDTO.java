package com.minhdunk.research.dto;

import com.minhdunk.research.utils.TestType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class TestHistoryOutputDTO {
    private Long id;
    private String title;
    private List<QuestionHistoryOutputDTO> questions;
    private Long submitterId;
    private TestType type;
    private Long testId;
    private Double totalScore;
    private LocalDateTime submitAt;
//    private Double durationInMinutes;
}
