package com.minhdunk.research.mapper;

import com.minhdunk.research.dto.DocumentInputDTO;
import com.minhdunk.research.dto.DocumentOutputDTO;
import com.minhdunk.research.dto.DocumentSearchOutputDTO;
import com.minhdunk.research.dto.DocumentWithLikeStatusDTO;
import com.minhdunk.research.entity.Document;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DocumentMapper {
    DocumentOutputDTO getDocumentOutputDtoFromDocument(Document document);
    @Mapping(target = "tests", ignore = true)
    @Mapping(target = "numberOfLikes", ignore = true)
    @Mapping(target = "likedByUsers", ignore = true)
    @Mapping(target = "thumbnail", ignore = true)
    @Mapping(target = "postTime", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "audio", ignore = true)
    @Mapping(target = "topic", source = "topic")
    Document getDocumentFromDocumentInputDto(DocumentInputDTO documentInputDTO);

    List<DocumentOutputDTO> getDocumentOutputDtosFromDocuments(List<Document> documents);

    @Mapping(target = "isLiked", ignore = true)
    DocumentWithLikeStatusDTO getDocumentWithLikeStatusDtoFromDocument(Document document);

    List<DocumentWithLikeStatusDTO> getDocumentWithLikeStatusDtosFromDocuments(List<Document> documents);


    @Mapping(target = "thumbnailId", source = "thumbnail.id")
    DocumentSearchOutputDTO getDocumentSearchOutputDtoFromDocument(Document document);

    List<DocumentSearchOutputDTO> getDocumentSearchOutputDtosFromDocuments(List<Document> documents);

}
