package com.minhdunk.research.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationOutputDTO {
    private Long id;
    private Long authorId;
    private String content;
    private LocalDateTime postTime;}
