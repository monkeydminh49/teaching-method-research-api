package com.minhdunk.research.controller;

import com.minhdunk.research.dto.BaseResponse;
import com.minhdunk.research.dto.PostInputDTO;
import com.minhdunk.research.dto.PostOutputDTO;
import com.minhdunk.research.entity.Media;
import com.minhdunk.research.entity.Post;
import com.minhdunk.research.mapper.PostMapper;
import com.minhdunk.research.service.MediaService;
import com.minhdunk.research.service.PostService;
import com.minhdunk.research.utils.PostOrientation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
@RestController
@RequestMapping("/api/v1")
public class PostController {
    @Autowired
    private MediaService mediaService;
    @Autowired
    private PostService postService;
    @Autowired
    private PostMapper postMapper;

    @PostMapping("/assignments/{id}/post")
    public BaseResponse submitAssignment(Principal principal,
                                         @RequestParam(value = "files", required = false) MultipartFile[] files,
                                         @RequestParam(value = "caption", required = false) String caption,
                                         @RequestParam(value = "caption", required = false) PostOrientation orientation ,
                                         @PathVariable Long id) {
        Set<Media> medias = new HashSet<>();
        for(MultipartFile file : files) {
            try {
                Media media = mediaService.persistMedia(file);
                medias.add(media);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        PostInputDTO request = PostInputDTO.builder()
                .caption(caption)
                .orientation(orientation)
                .build();
        Post post = postService.submitAssignment(principal ,id, request, medias);
        PostOutputDTO postOutputDTO = postMapper.getPostOutputDTOFromPost(post);
        return BaseResponse.builder()
                .status("ok")
                .data(postOutputDTO)
                .message("Submit assignment successfully")
                .build();
    }

}
