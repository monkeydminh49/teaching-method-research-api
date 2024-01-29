package com.minhdunk.research.controller;

import com.minhdunk.research.dto.DocumentInputDTO;
import com.minhdunk.research.dto.DocumentOutputDTO;
import com.minhdunk.research.entity.Document;
import com.minhdunk.research.entity.Media;
import com.minhdunk.research.mapper.DocumentMapper;
import com.minhdunk.research.service.DocumentService;
import com.minhdunk.research.service.MediaService;
import com.minhdunk.research.utils.DocumentType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/api/v1/documents")
public class DocumentController {
    @Autowired
    private DocumentService documentService;
    @Autowired
    private DocumentMapper documentMapper;
    @Autowired
    private MediaService mediaService;
    @PostMapping()
    public DocumentOutputDTO createDocument(
            Principal principal,
            @RequestParam(value = "audio", required = false) MultipartFile audio,
            @RequestParam(value = "thumbnail", required = false) MultipartFile thumbnail,
            @RequestParam(value = "title", required = true) String title,
            @RequestParam(value = "veryFirstText", required = false) String veryFirstText,
            @RequestParam(value = "type", required = false) DocumentType type,
            @RequestParam(value = "notionPageId", required = false) String notionPageId
    ) throws IOException {
        DocumentInputDTO request = new DocumentInputDTO(title, veryFirstText, type, notionPageId);
        log.info(notionPageId + " " + thumbnail.getName());
        Document document = null;
        document = documentService.createDocument(principal, request, audio, thumbnail);
        return documentMapper.getDocumentOutputDtoFromDocument(document);
    }

    @GetMapping()
    public List<DocumentOutputDTO> getDocuments(@RequestParam(value = "type", required = false) DocumentType type) {
        return documentMapper.getDocumentOutputDtosFromDocuments(documentService.getDocuments(type));
    }


    @GetMapping("/{id}")
    public DocumentOutputDTO getDocumentById(@PathVariable("id") Long id) {
        return documentMapper.getDocumentOutputDtoFromDocument(documentService.getDocumentById(id));
    }


}