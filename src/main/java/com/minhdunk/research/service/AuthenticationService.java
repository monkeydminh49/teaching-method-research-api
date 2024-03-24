package com.minhdunk.research.service;


import com.minhdunk.research.component.UserInfoUserDetails;
import com.minhdunk.research.dto.LoginRequestDTO;
import com.minhdunk.research.dto.RegisterRequestDTO;
import com.minhdunk.research.dto.UserOutputDTO;
import com.minhdunk.research.entity.User;
import com.minhdunk.research.exception.UserAlreadyExistsException;
import com.minhdunk.research.mapper.UserMapper;
import com.minhdunk.research.repository.UserRepository;
import com.minhdunk.research.utils.UserRole;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@Controller
@RestController
@RequestMapping("/api/v1")
@Slf4j
public class AuthenticationService {
    @Autowired
    private JavaMailSender mailSender;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(PasswordEncoder encoder, UserRepository userRepository, UserMapper userMapper, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.encoder = encoder;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public UserOutputDTO register(RegisterRequestDTO request) throws MessagingException, UnsupportedEncodingException {

        var user = userMapper.getUserFromRegisterRequestDTO(request);

        User userExists = userRepository.findByUsername(user.getUsername()).orElse(null);
        if (userExists != null) {
            log.info("User already exists");
            throw new UserAlreadyExistsException("User already exists");
        }
        if (request.getRole().equals("ROLE_TEACHER"))user.setRole(UserRole.ROLE_TEACHER);
        else user.setRole(UserRole.ROLE_STUDENT);
        user.setPassword(encoder.encode(user.getPassword()));

        String randomCode = RandomStringUtils.randomAlphanumeric(64);
        user.setVerificationCode(randomCode);
        user.setEnabled(false);

        var savedUser = userRepository.save(user);

        UserInfoUserDetails userDetails = new UserInfoUserDetails(user);
        String accessToken = jwtService.generateToken(userDetails);

        UserOutputDTO userOutputDTO = userMapper.getUserOutputDTOFromUser(savedUser);
        userOutputDTO.setToken(accessToken);


        return userOutputDTO;
    }

    public UserInfoUserDetails login(LoginRequestDTO request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        if (authentication.isAuthenticated()) {
            UserInfoUserDetails userDetails = (UserInfoUserDetails) authentication.getPrincipal();
            return userDetails;
        } else {
            log.info("Invalid login request! With username: " + request.getUsername() + " and password: " + request.getPassword());
            throw new UsernameNotFoundException("invalid login request! Please check the your username and password");
        }
    }

    @Async
    public void sendVerificationEmail(Authentication authentication, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        UserInfoUserDetails userDetails = (UserInfoUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        String toAddress = userDetails.getEmail();
        String fromAddress = "nguyendangminh03@gmail.com";
        String senderName = "BHA Education";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your email:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_blank\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "BHA Education.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getFirstName() + " " + user.getLastName());
        log.info(siteURL);
        siteURL = "http://densach.edu.vn";
        String verifyURL = siteURL + "/verify-email?code=" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);

    }


    public boolean verifyUserEmail(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode);

        if (user == null || user.getEnabled()) {
            return false;
        } else {
//            user.setVerificationCode(null);
            user.setEnabled(true);
            userRepository.save(user);

            return true;
        }

    }

}
