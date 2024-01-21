package com.minhdunk.research.service;

import com.minhdunk.research.dto.MediaOutputDTO;
import com.minhdunk.research.entity.Media;
import com.minhdunk.research.repository.MediaRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Service
@Slf4j
public class MediaService {
    @Autowired
    private MediaRepository mediaRepository;

    @Value("${application.media-directory}")
    private String FOLDER_PATH;

    @Transactional(rollbackOn = {Exception.class, Throwable.class})
    public Media uploadFileToFileSystem(MultipartFile file, String description) throws IOException {
        Media media = uploadFileToFileSystem(file);
        media.setDescription(description);
        return media;
    }

    @Transactional(rollbackOn = {Exception.class, Throwable.class})
    public Media uploadFileToFileSystem(MultipartFile file) throws IOException {
        // Add timestamp to file name
        String fileName = file.getOriginalFilename();
        String[] fileNameParts = fileName.split("\\.");
        String fileExtension = fileNameParts[fileNameParts.length - 1];
        String fileNameWithoutExtension = fileName.substring(0, fileName.length() - fileExtension.length() - 1);
        String fileNameWithTimestamp = fileNameWithoutExtension + "_" + System.currentTimeMillis() + "." + fileExtension;
        String filePath = FOLDER_PATH + fileNameWithTimestamp;


        // Create folder if not exits
        File folder = new File(FOLDER_PATH);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        Media media = Media.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .filePath(filePath)
                .size(file.getSize())
                .build();
        File f = new File(filePath);
        file.transferTo(f);
        log.info("File path: " + f.getAbsolutePath());

        return media;
    }

    @Transactional(rollbackOn = {Exception.class, Throwable.class})
    public Media persistMedia(MultipartFile file) throws IOException {
        Media media = uploadFileToFileSystem(file);
        return mediaRepository.save(media);
    }

    @Transactional(rollbackOn = {Exception.class, Throwable.class})
    public Media persistMedia(MultipartFile file, String description) throws IOException {
        Media media = uploadFileToFileSystem(file, description);
        return mediaRepository.save(media);
    }



    public ResponseEntity<?> downloadFileFromFileSystem(String id) throws IOException {
        Optional<Media> fileData = mediaRepository.findById(id);
        String filePath = fileData.get().getFilePath();
        byte[] file = Files.readAllBytes(new File(filePath).toPath());
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(fileData.get().getType()))
                .body(file);
    }

    public Media getMediaById(String id){
        return mediaRepository.findById(id).get();
    }
}
