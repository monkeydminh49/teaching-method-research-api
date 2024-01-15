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
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("/api/v1")
@Slf4j
public class AuthenticationService {
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

    public UserOutputDTO register(RegisterRequestDTO request) {

        var user = userMapper.getUserFromRegisterRequestDTO(request);

        User userExists = userRepository.findByUsername(user.getUsername()).orElse(null);
        if (userExists != null) {
            log.info("User already exists");
            throw new UserAlreadyExistsException("User already exists");
        }
        if (request.getRole().equals("ROLE_TEACHER"))user.setRole(UserRole.ROLE_TEACHER);
        else user.setRole(UserRole.ROLE_STUDENT);
        user.setPassword(encoder.encode(user.getPassword()));

        var savedUser = userRepository.save(user);

        UserInfoUserDetails userDetails = new UserInfoUserDetails(user);
        String accessToken = jwtService.generateToken(userDetails);

        UserOutputDTO userOutputDTO = userMapper.getUserOutputDTOFromUser(savedUser);
        userOutputDTO.setToken(accessToken);

        return userOutputDTO;
    }

    public UserOutputDTO login(LoginRequestDTO request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        if (authentication.isAuthenticated()) {
            UserInfoUserDetails userDetails = (UserInfoUserDetails) authentication.getPrincipal();

            var user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow();

            String accessToken = jwtService.generateToken(userDetails);

            UserOutputDTO userOutputDTO = userMapper.getUserOutputDTOFromUser(user);
            userOutputDTO.setToken(accessToken);
            return userOutputDTO;
        } else {
            log.info("Invalid login request! With username: " + request.getUsername() + " and password: " + request.getPassword());
            throw new UsernameNotFoundException("invalid login request! Please check the your username and password");
        }
    }

}
