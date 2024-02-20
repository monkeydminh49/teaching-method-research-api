package com.minhdunk.research.controller;


import com.minhdunk.research.dto.BaseResponse;
import com.minhdunk.research.dto.LoginRequestDTO;
import com.minhdunk.research.dto.RegisterRequestDTO;
import com.minhdunk.research.dto.UserOutputDTO;
import com.minhdunk.research.service.AuthenticationService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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

    @GetMapping("/send-verification-email")
    public BaseResponse sendVerificationEmail(Authentication authentication, HttpServletRequest httpRequest) throws MessagingException, UnsupportedEncodingException {
        authenticationService.sendVerificationEmail(authentication, getSiteURL(httpRequest));
        return new BaseResponse("ok","Verification email sent successfully", null);
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    @GetMapping("/verify-email")
    public BaseResponse verifyUserEmail(@Param("code") String code) {
        if (authenticationService.verifyUserEmail(code)) {
            return new BaseResponse("ok","verify_success", null);
        } else {
            return new BaseResponse("error","verify_failed", null);
        }
    }



    @PostMapping("/login")
    public UserOutputDTO login(@RequestBody LoginRequestDTO request) {
        UserOutputDTO userResponse =  authenticationService.login(request);
        log.info("User " + request.getUsername() + " login successfully");
        return userResponse;
    }

}
