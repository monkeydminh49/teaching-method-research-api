package com.minhdunk.research.mapper;

import com.minhdunk.research.dto.CommentInputDTO;
import com.minhdunk.research.dto.CommentOutputDTO;
import com.minhdunk.research.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "userLastName", source = "user.lastName")
    @Mapping(target = "userFirstName", source = "user.firstName")
    @Mapping(target = "userId", source = "user.id")
    CommentOutputDTO getCommentOutputDtoFromComment(Comment comment);

    @Mapping(target = "type", ignore = true)
    @Mapping(target = "destinationId", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "postTime", ignore = true)
    @Mapping(target = "id", ignore = true)
    Comment getCommentFromCommentInputDto(CommentInputDTO commentInputDTO);

    List<CommentOutputDTO> getCommentOutputDTOsFromComments(List<Comment> comments);
}
