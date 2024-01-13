package com.minhdunk.research.config;


import com.minhdunk.research.entity.User;
import com.minhdunk.research.repository.UserRepository;
import com.minhdunk.research.utils.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class InitConfig {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            User admin = userRepository.findByEmail("admin@gmail.com").orElse(null);
            if (admin == null) {
                admin = new User();
                admin.setName("admin");
                admin.setEmail("admin@gmail.com");
                admin.setPassword(passwordEncoder.encode("123456"));
                admin.setRoles(List.of(UserRole.ROLE_ADMIN));
                userRepository.save(admin);
            }
        };
    }
}
