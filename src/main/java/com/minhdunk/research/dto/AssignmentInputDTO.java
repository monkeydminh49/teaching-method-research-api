package com.minhdunk.research.dto;

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
    public LocalDateTime getDueDateTime(){
        return this.dueDateTime.truncatedTo(ChronoUnit.SECONDS);
    }
}
