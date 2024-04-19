package com.minhdunk.research.dto;

import com.minhdunk.research.utils.PostOrientation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class CounsellingOutputDTO {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createAt;
    private Long documentId;
    private PostOrientation orientation;
}
