package com.minhdunk.research.controller;


import com.minhdunk.research.dto.LoginRequestDTO;
import com.minhdunk.research.dto.RegisterRequestDTO;
import com.minhdunk.research.dto.UserOutputDTO;
import com.minhdunk.research.service.AuthenticationService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@Controller
@RestController
@RequestMapping("/api/v1")
@Slf4j
@CrossOrigin
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public UserOutputDTO registerUser(@RequestBody RegisterRequestDTO request) throws MessagingException, UnsupportedEncodingException {
        UserOutputDTO userResponse = authenticationService.register(request);
        log.info("Test loggggggg");
        return userResponse;
    }

    @PostMapping("/send-verification-email")
    public String sendVerificationEmail(Authentication authentication, HttpServletRequest httpRequest) throws MessagingException, UnsupportedEncodingException {
        authenticationService.sendVerificationEmail(authentication, httpRequest.getRequestURI());
        return "Verification email sent";
    }



    @PostMapping("/login")
    public UserOutputDTO login(@RequestBody LoginRequestDTO request) {
        UserOutputDTO userResponse =  authenticationService.login(request);
        log.info("User " + request.getUsername() + " login successfully");
        return userResponse;
    }

}
