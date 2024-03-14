package com.minhdunk.research.controller;

import com.minhdunk.research.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
}
