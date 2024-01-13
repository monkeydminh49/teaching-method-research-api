package com.minhdunk.research.dto;

import com.minhdunk.research.utils.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private List<UserRole> roles;
    private String token;
    private Date dateOfBirth;
}
