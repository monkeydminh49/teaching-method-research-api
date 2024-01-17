package com.minhdunk.research.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationOutputDTO {
    private Long id;
    private Long authorId;
    private String content;
    private LocalDateTime postTime;
    public LocalDateTime getPostTime(){
        return this.postTime.truncatedTo(ChronoUnit.SECONDS);
    }
}
