package com.minhdunk.research.mapper;

import com.minhdunk.research.dto.MediaOutputDTO;
import com.minhdunk.research.entity.Media;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MediaMapper {
    MediaOutputDTO getMediaOutputDTOFromMedia(Media media);
}
