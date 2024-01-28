package com.minhdunk.research.mapper;

import com.minhdunk.research.dto.DocumentInputDTO;
import com.minhdunk.research.dto.DocumentOutputDTO;
import com.minhdunk.research.entity.Document;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DocumentMapper {
    DocumentOutputDTO getDocumentOutputDtoFromDocument(Document document);
    @Mapping(target = "postTime", ignore = true)
    @Mapping(target = "notionPageId", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "audio", ignore = true)
    Document getDocumentFromDocumentInputDto(DocumentInputDTO documentInputDTO);

    List<DocumentOutputDTO> getDocumentOutputDtosFromDocuments(List<Document> documents);
}
