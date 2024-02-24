package com.minhdunk.research.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomOutputDTO {
    private Long id;
    private String name;
    private String code;
    private Long teacherId;
    private String teacherAvatarId;
    private String teacherFirstName;
    private String teacherLastName;
    private int numberOfStudents;
    private Iterable<UserOutputDTO> students;
}
