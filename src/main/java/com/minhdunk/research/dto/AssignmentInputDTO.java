package com.minhdunk.research.dto;

import com.minhdunk.research.utils.AssignmentType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.security.DenyAll;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentInputDTO {
    private String title;
    private String content;
    private LocalDateTime dueDateTime;
    private Boolean isForGroup;
    private Long documentId;
    @Schema(description = "Type of the assignment", example = "OTHER | FOR_TEST | FOR_COUNSELLING")
    private AssignmentType type = AssignmentType.OTHER;
    private Long relatedTestId;
    public LocalDateTime getDueDateTime(){
        return this.dueDateTime.truncatedTo(ChronoUnit.SECONDS);
    }
}
