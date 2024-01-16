package com.minhdunk.research.classroom;

import com.minhdunk.research.dto.UserOutputDTO;
import com.minhdunk.research.entity.Classroom;
import com.minhdunk.research.service.AuthenticationService;
import com.minhdunk.research.service.ClassroomService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class ClassroomServiceTest {

    @Autowired
    private ClassroomService classroomService;
    @Autowired
    AuthenticationService authenticationService;

    @Test
    void itShouldCreateANewClass(){

        Classroom classroom = classroomService.getClassroomById(2L);
        assertThat(classroom.getId()).isEqualTo(2L);
    }

}
