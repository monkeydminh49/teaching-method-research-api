package com.minhdunk.research.controller;

import com.minhdunk.research.service.ChoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ChoiceController {
    @Autowired
    private ChoiceService choiceService;
}
