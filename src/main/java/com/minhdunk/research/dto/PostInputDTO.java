package com.minhdunk.research.dto;

import com.minhdunk.research.utils.PostOrientation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostInputDTO {
    private String title;
    private String caption;
    private PostOrientation orientation;
}
