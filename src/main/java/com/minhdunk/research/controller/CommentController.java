package com.minhdunk.research.controller;

import com.minhdunk.research.dto.BaseResponse;
import com.minhdunk.research.dto.CommentInputDTO;
import com.minhdunk.research.dto.CommentOutputDTO;
import com.minhdunk.research.entity.Comment;
import com.minhdunk.research.mapper.CommentMapper;
import com.minhdunk.research.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin
@RestController
@Controller
@RequestMapping("/api/v1/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentMapper commentMapper;

    @PostMapping("/post/{postId}")
    public CommentOutputDTO postCommentToPostId(Principal principal, @PathVariable("postId") Long postId, @RequestBody CommentInputDTO request){
        Comment comment =  commentService.postCommentToPostId(principal, postId, request);
        return commentMapper.getCommentOutputDtoFromComment(comment);
    }

    @PostMapping("/notification/{notification-id}")
    public CommentOutputDTO postCommentToNotificationId(Principal principal, @PathVariable("notification-id") Long notificationId, @RequestBody CommentInputDTO request){
        Comment comment =  commentService.postCommentToNotificationId(principal, notificationId, request);
        return commentMapper.getCommentOutputDtoFromComment(comment);
    }

    @GetMapping("/{commentId}")
    public CommentOutputDTO getCommentById(@PathVariable("commentId") Long commentId){
        return commentService.getCommentOutputDtoById(commentId);
    }

    @PutMapping("/pin/{commentId}")
    public BaseResponse pinComment(@PathVariable("commentId") Long commentId){
        commentService.pinComment(commentId);
        return new BaseResponse( "ok", "Pinned comment successfully", null);
    }

    @PutMapping("/unpin/{commentId}")
    public BaseResponse unpinComment(@PathVariable("commentId") Long commentId){
        commentService.unpinComment(commentId);
        return new BaseResponse( "ok", "Unpinned comment successfully", null);
    }

}
