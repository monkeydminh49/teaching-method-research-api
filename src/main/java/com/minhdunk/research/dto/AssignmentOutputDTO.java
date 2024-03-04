package com.minhdunk.research.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentOutputDTO {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime dueDateTime;
    private LocalDateTime assignedDateTime;
    private Boolean isForGroup;

    public LocalDateTime getDueDateTime(){
        return this.dueDateTime.truncatedTo(ChronoUnit.SECONDS);
    }
    public LocalDateTime getAssignedDateTime(){
        return this.assignedDateTime.truncatedTo(ChronoUnit.SECONDS);
    }
}
