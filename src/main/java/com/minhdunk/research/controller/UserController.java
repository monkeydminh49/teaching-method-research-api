package com.minhdunk.research.controller;

import com.minhdunk.research.dto.UserOutputDTO;
import com.minhdunk.research.mapper.UserMapper;
import com.minhdunk.research.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@CrossOrigin
@Controller
@RestController
@RequestMapping("/api/v1")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/user")
    private UserOutputDTO getUserByUsernameInPrincipal(Principal principal){
        return userMapper.getUserOutputDTOFromUser(userService.getUserByUsername(principal.getName()));
    }
}
