package com.minhdunk.research.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("/api/v1")
public class WelcomeController {

    @GetMapping()
    public String hello(){
        return "Welcome to Nghien cuu khoa hoc";
    }
}
