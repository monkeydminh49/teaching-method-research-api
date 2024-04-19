package com.minhdunk.research.dto;


import com.minhdunk.research.utils.PostOrientation;
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
public class CounsellingInputDTO {
    private String title;
    private String content;
    private PostOrientation orientation = PostOrientation.SOCIAL;
}
