package com.minhdunk.research.config;


import com.minhdunk.research.document.DocumentElastic;
import com.minhdunk.research.entity.User;
import com.minhdunk.research.repository.UserRepository;
import com.minhdunk.research.utils.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class InitConfig {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            DocumentElastic documentElastic = new DocumentElastic();
            documentElastic.setTitle("Test elastic");

//            documentElasticRepository.save(documentElastic);
            User admin = userRepository.findByUsername("admin").orElse(null);
            if (admin == null) {
                admin = new User();
                admin.setFirstName("admin");
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("123456"));
                admin.setRole(UserRole.ROLE_ADMIN);
                userRepository.save(admin);
            }
        };
    }
}
