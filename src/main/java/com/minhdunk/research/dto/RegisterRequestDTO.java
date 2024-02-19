package com.minhdunk.research.dto;

import com.minhdunk.research.utils.UserGender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequestDTO {
    private String firstName;
    private String username;
    private String lastName;
    private String password;
    private LocalDate dateOfBirth;
    private String email;
    @Schema(description = "Gender of user", example = "MALE | FEMALE | OTHER")
    private UserGender gender;
    @Schema(description = "Role of user", example = "ROLE_STUDENT | ROLE_TEACHER")
    private String role;
}
