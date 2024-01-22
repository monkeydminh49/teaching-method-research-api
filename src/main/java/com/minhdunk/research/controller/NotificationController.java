package com.minhdunk.research.controller;

import com.minhdunk.research.dto.CommentOutputDTO;
import com.minhdunk.research.entity.Comment;
import com.minhdunk.research.mapper.CommentMapper;
import com.minhdunk.research.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Controller
@RestController
@RequestMapping("/api/v1/notifications")
@Slf4j
public class NotificationController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentMapper commentMapper;

    @GetMapping("/{notification-id}/comments")
    public List<CommentOutputDTO> getAllNotificationComment(@PathVariable("notification-id") Long notificationId){
        List<Comment> comments =  commentService.getAllCommentByNotificationId(notificationId);
        return commentMapper.getCommentOutputDTOsFromComments(comments);
    }
}
