package com.minhdunk.research.dto;

import com.minhdunk.research.utils.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserOutputDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private UserRole role;
    private String token;
    private LocalDate dateOfBirth;

    public UserOutputDTO (Long id, String firstName, String lastName, String username, UserRole role, LocalDate dateOfBirth) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.role = role;
        this.dateOfBirth = dateOfBirth;
    }
}
