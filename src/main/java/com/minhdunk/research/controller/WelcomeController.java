package com.minhdunk.research.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", maxAge = 3600)
public class WelcomeController {

    @GetMapping("/hello")
    public String hello(){
        return "Welcome to Nghien cuu khoa hoc";
    }

    @GetMapping("/hello-admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String hello2(){
        return "Welcome Admin to Nghien cuu khoa hoc";
    }

    @GetMapping("/hello-student")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public String hello3(){
        return "Welcome Student to Nghien cuu khoa hoc";
    }

    @GetMapping("/hello-teacher")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public String hello4(){
        return "Welcome Teacher to Nghien cuu khoa hoc";
    }
}
