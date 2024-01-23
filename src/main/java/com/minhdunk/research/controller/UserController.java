package com.minhdunk.research.controller;

import com.minhdunk.research.dto.UserOutputDTO;
import com.minhdunk.research.mapper.UserMapper;
import com.minhdunk.research.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @GetMapping("/users/{id}")
    private UserOutputDTO getUserById(@PathVariable Long id){
        return userMapper.getUserOutputDTOFromUser(userService.getUserById(id));
    }

    @PostMapping("/users/update-avatar")
    private UserOutputDTO updateAvatar(Principal principal ,@RequestParam("file") MultipartFile avatar) throws IOException {
        return userMapper.getUserOutputDTOFromUser(userService.updateAvatar(principal, avatar));
    }

}
