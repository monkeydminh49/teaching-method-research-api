package com.minhdunk.research.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomOutputInListDTO {
    private Long id;
    private String name;
    private String code;
    private Long teacherId;
    private Long teacherAvatarId;
    private String teacherFirstName;
    private String teacherLastName;
    private int numberOfStudents;
}
