package com.minhdunk.research.service;

import com.minhdunk.research.repository.ClassroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class ClassroomCodeGenerator {
    private static final int CODE_LENGTH = 7;
    private static final String CODE_CHARS = "abcdefghijklmnopqrstuvwxyz0123456789";
    private final SecureRandom secureRandom = new SecureRandom();

    @Autowired
    private ClassroomRepository classroomRepository;

    public String generateUniqueClassroomCode() {
        String code;
        do {
            code = generateClassroomCode();
        } while (classroomRepository.findByCode(code).isPresent());

        return code;
    }

    private String generateClassroomCode() {
        StringBuilder codeBuilder = new StringBuilder();

        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomIndex = secureRandom.nextInt(CODE_CHARS.length());
            char randomChar = CODE_CHARS.charAt(randomIndex);
            codeBuilder.append(randomChar);
        }

        return codeBuilder.toString();
    }
}
