package com.minhdunk.research.dto;


import com.minhdunk.research.entity.Assignment;
import com.minhdunk.research.entity.Post;
import com.minhdunk.research.entity.PostType;
import com.minhdunk.research.utils.AssignmentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentStatusOutputDTO {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime dueDateTime;
    private LocalDateTime assignedDateTime;
    private AssignmentType type;
    private String status;
    private Long relatedTestId;
    public AssignmentStatusOutputDTO(Assignment assignment, Post post) {
        this.id = assignment.getId();
        this.title = assignment.getTitle();
        this.content = assignment.getContent();
        this.dueDateTime = assignment.getDueDateTime();
        this.assignedDateTime = assignment.getAssignedDateTime();
        this.type = assignment.getType();
        this.relatedTestId = assignment.getRelatedTest() == null ? null : assignment.getRelatedTest().getId();
        if (post == null || post.getType() == null){
            this.status = "NOT_SUBMITTED";
            return;
        }
        this.status = post.getType().toString();
    }

    public LocalDateTime getDueDateTime(){
        return this.dueDateTime.truncatedTo(ChronoUnit.SECONDS);
    }
    public LocalDateTime getAssignedDateTime(){
        return this.assignedDateTime.truncatedTo(ChronoUnit.SECONDS);
    }
}
