package com.minhdunk.research.dto;

import com.minhdunk.research.entity.Assignment;
import com.minhdunk.research.utils.PostOrientation;
import com.minhdunk.research.utils.PostType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostOutputDTO {
    private Long id;
    private Long authorId;
    private Long assignmentId;
    private String caption;
    private PostOrientation orientation;
    private PostType type;

}
