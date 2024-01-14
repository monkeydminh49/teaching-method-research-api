package com.minhdunk.research.controller;


import com.minhdunk.research.dto.LoginRequestDTO;
import com.minhdunk.research.dto.RegisterRequestDTO;
import com.minhdunk.research.dto.UserOutputDTO;
import com.minhdunk.research.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("/api/v1")
@Slf4j
@CrossOrigin
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public UserOutputDTO registerUser(@RequestBody RegisterRequestDTO request) {
        UserOutputDTO userResponse = authenticationService.register(request);
        log.info("Test loggggggg");
        return userResponse;
    }

    @PostMapping("/login")
    public UserOutputDTO login(@RequestBody LoginRequestDTO request) {
        UserOutputDTO userResponse =  authenticationService.login(request);
        log.info("User " + request.getUsername() + " login successfully");
        return userResponse;
    }

//    @PostMapping("/refresh-token")
//    public MappingResponse refreshToken(@RequestBody RefreshTokenRequest request) {
//        JwtResponse token =  authenticationService.refreshToken(request);
//        return MappingResponse.builder()
//                .status("ok")
//                .body(token)
//                .message("Refresh token successfully")
//                .build();
//    }
}
