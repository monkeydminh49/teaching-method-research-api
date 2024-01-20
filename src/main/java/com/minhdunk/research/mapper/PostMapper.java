package com.minhdunk.research.mapper;

import com.minhdunk.research.dto.PostOutputDTO;
import com.minhdunk.research.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "assignmentId", source = "assignment.id")
    PostOutputDTO getPostOutputDTOFromPost(Post post);

    List<PostOutputDTO> getPostOutputDTOsFromPosts(List<Post> posts);
}
