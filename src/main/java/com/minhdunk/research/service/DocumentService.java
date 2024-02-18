package com.minhdunk.research.service;

import com.minhdunk.research.dto.DocumentInputDTO;
import com.minhdunk.research.dto.DocumentWithLikeStatusDTO;
import com.minhdunk.research.entity.Document;
import com.minhdunk.research.entity.DocumentUser;
import com.minhdunk.research.entity.Media;
import com.minhdunk.research.entity.User;
import com.minhdunk.research.exception.NotFoundException;
import com.minhdunk.research.mapper.DocumentMapper;
import com.minhdunk.research.repository.DocumentRepository;
import com.minhdunk.research.repository.DocumentUserRepository;
import com.minhdunk.research.repository.MediaRepository;
import com.minhdunk.research.utils.DocumentType;
import com.minhdunk.research.utils.DocumentUserKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@Slf4j
public class DocumentService {
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private DocumentMapper documentMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private MediaService mediaService;

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private DocumentUserRepository documentUserRepository;

    public Document createDocument(Principal principal, DocumentInputDTO request, MultipartFile audio, MultipartFile thumbnail) throws IOException {
        User user = userService.getUserByUsername(principal.getName());
        Document document = documentMapper.getDocumentFromDocumentInputDto(request);
        document.setAuthor(user);
        ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
        document.setPostTime(LocalDateTime.now(zoneId));
        document.setNumberOfLikes(0);
        log.info("Document: {}", document.getTopic());
        if (thumbnail != null) {
            Media savedThumbnail = mediaService.persistMedia(thumbnail);
            document.setThumbnail(savedThumbnail);
        }
        if (audio != null) {
            Media savedAudio = mediaService.persistMedia(audio);
            document.setAudio(savedAudio);
        }
        return documentRepository.save(document);
    }

    // TODO: Resolve n + 1 problem
    public Document getDocumentById(Long id) {
        return documentRepository.findById(id).orElseThrow(() -> new NotFoundException("Document with id " + id + " not found"));
    }

    // TODO: Pagination
    public List<Document> getDocuments(DocumentType type) {
        if (type != null) {
            return documentRepository.findAllByType(type);
        }
        return documentRepository.findAll();
    }

    public void deleteDocument(Long id) {
        Document document = getDocumentById(id);
        if(document.getAudio()!= null) mediaRepository.delete(document.getAudio());
        if(document.getThumbnail()!= null) mediaRepository.delete(document.getThumbnail());
        documentRepository.delete(document);
    }

    public void likeDocument(Long id, Principal principal) {
        Document document = getDocumentById(id);
        User user = userService.getUserByUsername(principal.getName());
        ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
        DocumentUser documentUser = new DocumentUser(new DocumentUserKey(document.getId(), user.getId()), document, user, LocalDateTime.now(zoneId));
        if (documentUserRepository.findById(new DocumentUserKey(document.getId(), user.getId())).isPresent()) {
            return;
        }

        documentUserRepository.save(documentUser);
        document.setNumberOfLikes(document.getNumberOfLikes() + 1);
        documentRepository.save(document);
    }

    public void unlikeDocument(Long id, Principal principal) {
        Document document = getDocumentById(id);
        User user = userService.getUserByUsername(principal.getName());
        if (documentUserRepository.findById(new DocumentUserKey(document.getId(), user.getId())).isEmpty()) {
            return;
        }
        documentUserRepository.deleteById(new DocumentUserKey(document.getId(), user.getId()));
        document.setNumberOfLikes(document.getNumberOfLikes() - 1);
        documentRepository.save(document);
    }

    public DocumentWithLikeStatusDTO getDocumentWithLikeStatus(Long id, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        return documentRepository.getDocumentWithLikeStatus(id, user.getId()).orElseThrow(() -> new NotFoundException("Document with id " + id + " not found"));
    }
}
