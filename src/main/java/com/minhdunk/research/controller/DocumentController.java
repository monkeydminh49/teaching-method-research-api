package com.minhdunk.research.controller;

import com.minhdunk.research.dto.*;
import com.minhdunk.research.entity.Document;
import com.minhdunk.research.mapper.DocumentMapper;
import com.minhdunk.research.mapper.TestMapper;
import com.minhdunk.research.service.DocumentService;
import com.minhdunk.research.service.MediaService;
import com.minhdunk.research.service.TestService;
import com.minhdunk.research.utils.DocumentTopic;
import com.minhdunk.research.utils.DocumentType;
import com.minhdunk.research.utils.TestType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
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
    @Autowired
    private TestService testService;
    @Autowired
    private TestMapper testMapper;
    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public DocumentOutputDTO createDocument(
            Principal principal,
            @RequestParam(value = "audio", required = false) MultipartFile audio,
            @RequestParam(value = "thumbnail", required = false) MultipartFile thumbnail,
            @RequestParam(value = "title", required = true) String title,
            @RequestParam(value = "veryFirstText", required = false) String veryFirstText,
            @RequestParam(value = "type", required = false) DocumentType type,
            @RequestParam(value = "notionPageId", required = false) String notionPageId,
            @RequestParam(value = "topic", required = false) DocumentTopic topic
            ) throws IOException {
        DocumentInputDTO request = new DocumentInputDTO(title, veryFirstText, type, notionPageId, topic.toString());
        log.info("Request: {}", request.getTopic());
        Document document = null;
        document = documentService.createDocument(principal, request, audio, thumbnail);
        return documentMapper.getDocumentOutputDtoFromDocument(document);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<DocumentOutputDTO> getDocuments(
            @RequestParam(value = "type", required = false) DocumentType type,
            @RequestParam(value = "topic", required = false) DocumentTopic topic) {
        return documentMapper.getDocumentOutputDtosFromDocuments(documentService.getDocuments(type, topic == null ? null : String.valueOf(topic)));
    }


    @GetMapping("/{documentId}/tests")
    public TestDTO getTestsByDocumentId(@PathVariable Long documentId, @RequestParam TestType type) {
        return testMapper.getTestDTOFromTest(testService.getTestsByDocumentIdAndType(documentId, type));
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DocumentOutputDTO getDocumentById(@PathVariable("id") Long id) {
        return documentMapper.getDocumentOutputDtoFromDocument(documentService.getDocumentById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse deleteDocument(@PathVariable("id") Long id) {
        documentService.deleteDocument(id);
        return BaseResponse.builder()
                .status("ok")
                .message("Delete document successfully!")
                .data(null)
                .build();
    }

    @GetMapping("/detail/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DocumentWithLikeStatusDTO getDocumentWithLikeStatus(@PathVariable("id") Long id, Authentication authentication) {
        return documentService.getDocumentWithLikeStatus(id, authentication);
    }

    @GetMapping("/detail")
    @ResponseStatus(HttpStatus.OK)
    public List<DocumentWithLikeStatusDTO> getDocumentsWithLikeStatus(
            @RequestParam(value = "type", required = false) DocumentType type,
            @RequestParam(value = "topic", required = false) DocumentTopic topic,
            Authentication authentication) {
        return documentService.getDocumentsWithLikeStatus(type, topic,  authentication);
    }


}
