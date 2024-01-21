package com.minhdunk.research.controller;

import com.minhdunk.research.dto.BaseResponse;
import com.minhdunk.research.dto.MediaOutputDTO;
import com.minhdunk.research.entity.Media;
import com.minhdunk.research.mapper.MediaMapper;
import com.minhdunk.research.service.MediaService;
import com.minhdunk.research.service.StreamingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;
@CrossOrigin
@Controller
@RestController
@RequestMapping("/api/v1/media")
public class MediaController {
    @Autowired
    private MediaService mediaService;
    @Autowired
    private StreamingService streamingService;
    @Autowired
    private MediaMapper mediaMapper;

    @PostMapping()
    public BaseResponse uploadFileToFIleSystem(@RequestParam("file") MultipartFile file, @RequestParam(value = "description", required = false) String description) throws IOException {
        Media uploadedMedia = mediaService.persistMedia(file, description);
        MediaOutputDTO mediaOutputDTO = mediaMapper.getMediaOutputDTOFromMedia(uploadedMedia);
        return BaseResponse.builder()
                .status("ok")
                .data(mediaOutputDTO)
                .message("Upload file " + uploadedMedia.getName() + " successfully")
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable String id) throws IOException {
        return mediaService.downloadFileFromFileSystem(id);
    }

    @GetMapping(value = "/stream/{id}", produces = {"audio/mpeg", "video/mp4"})
    public Mono<Resource> getStreamingMedia(@PathVariable String id) {
//        System.out.println("range in bytes() : " + range);
        return streamingService.getStreamingMedia(id);
    }
}
