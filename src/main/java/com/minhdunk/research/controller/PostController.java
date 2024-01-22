package com.minhdunk.research.controller;

import com.minhdunk.research.dto.BaseResponse;
import com.minhdunk.research.dto.CommentOutputDTO;
import com.minhdunk.research.dto.PostInputDTO;
import com.minhdunk.research.dto.PostOutputDTO;
import com.minhdunk.research.entity.Comment;
import com.minhdunk.research.entity.Media;
import com.minhdunk.research.entity.Post;
import com.minhdunk.research.mapper.CommentMapper;
import com.minhdunk.research.mapper.PostMapper;
import com.minhdunk.research.service.CommentService;
import com.minhdunk.research.service.MediaService;
import com.minhdunk.research.service.PostService;
import com.minhdunk.research.utils.PostAction;
import com.minhdunk.research.utils.PostOrientation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@CrossOrigin
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
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentMapper commentMapper;


    @PostMapping("/assignments/{id}/post")
    public BaseResponse submitAssignment(Principal principal,
                                         @RequestParam(value = "files", required = false) MultipartFile[] files,
                                         @RequestParam(value = "caption", required = false) String caption,
                                         @RequestParam(value = "orientation", required = false) PostOrientation orientation ,
                                         @RequestParam(value = "member-ids", required = false) Long[] memberIds,
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
        Post post = postService.submitAssignment(principal ,id, request, medias, memberIds);
        PostOutputDTO postOutputDTO = postMapper.getPostOutputDTOFromPost(post);
        return BaseResponse.builder()
                .status("ok")
                .data(postOutputDTO)
                .message("Submit assignment successfully")
                .build();
    }



    @GetMapping("/posts/{postId}")
    public PostOutputDTO getPostById(@PathVariable Long postId) {
        Post post = postService.getPostByIdWithMedias(postId);
        return postMapper.getPostOutputDTOFromPost(post);
    }

    @PutMapping("/posts/{postId}/action/{action}")
    public PostOutputDTO updatePostType(Principal principal, @PathVariable Long postId, @PathVariable @Parameter(name = "action", description = "approve | reject", example = "approve") String action) {
        PostAction postAction = PostAction.valueOf(action.toUpperCase());
        Post post = postService.updatePostType(principal, postId, postAction);
        return postMapper.getPostOutputDTOFromPost(post);
    }
    @GetMapping("/posts/{postId}/comments")
    public List<CommentOutputDTO> getAllPostComment(@PathVariable("postId") Long postId){
        List<Comment> comments =  commentService.getAllCommentByPostId(postId);
        return commentMapper.getCommentOutputDTOsFromComments(comments);
    }
}
