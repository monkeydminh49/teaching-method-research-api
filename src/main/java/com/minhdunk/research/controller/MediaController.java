package com.minhdunk.research.controller;

import com.minhdunk.research.dto.BaseResponse;
import com.minhdunk.research.dto.MediaInputDTO;
import com.minhdunk.research.dto.MediaOutputDTO;
import com.minhdunk.research.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Controller
@RestController
@RequestMapping("/api/v1")
public class MediaController {
    @Autowired
    private MediaService mediaService;

    @PostMapping("/file-system")
    public BaseResponse uploadFileToFIleSystem(@RequestParam("file") MultipartFile file, @RequestParam("description") String description) throws IOException {
        MediaOutputDTO uploadedMedia = mediaService.uploadFileToFileSystem(file, description);
        return BaseResponse.builder()
                .status("ok")
                .data(uploadedMedia)
                .message("Upload file " + uploadedMedia.getName() + " successfully")
                .build();
    }

    @GetMapping("/file-system/{id}")
    public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable String id) throws IOException {
        return mediaService.downloadFileFromFileSystem(id);

    }
}
