package com.minhdunk.research.service;

import com.minhdunk.research.dto.ClassroomInputDTO;
import com.minhdunk.research.entity.Classroom;
import com.minhdunk.research.entity.User;
import com.minhdunk.research.exception.NotFoundException;
import com.minhdunk.research.mapper.ClassroomMapper;
import com.minhdunk.research.repository.ClassroomRepository;
import com.minhdunk.research.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ClassroomService {
    @Autowired
    private ClassroomRepository classRoomRepository;
    @Autowired
    private ClassroomMapper classRoomMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClassroomCodeGenerator classroomCodeGenerator;
    public Classroom createClassRoom(Principal principal, ClassroomInputDTO request) {
        Classroom classRoom = classRoomMapper.getClassRoomFromClassRoomInputDTO(request);
        // Set teacher for classroom
        User teacher = userRepository.findByUsername(principal.getName()).get();

        // Generate unique code with numbers and characters for classroom
        String code = classroomCodeGenerator.generateUniqueClassroomCode();
        classRoom.setCode(code);
        classRoom.setTeacher(teacher);
        return classRoomRepository.save(classRoom);
    }

    public Classroom getClassroomById(Long id) {
        Classroom classroom = classRoomRepository.findById(id).orElseThrow(() -> new NotFoundException("Classroom not found"));
        log.info(String.valueOf(classroom.getStudents().size()));
        return classroom;
    }

    public Classroom getClassroomByCode(String code) {
        return classRoomRepository.findByCode(code).orElseThrow(() -> new NotFoundException("Classroom not found"));
    }

    public void joinClassroom(String code, Principal principal) {
        Classroom classroom = getClassroomByCode(code);
        User student = userRepository.findByUsername(principal.getName()).get();
        classroom.addStudent(student);
        classRoomRepository.save(classroom);
    }

    public List<User> getStudentsByClassroomId(Long id) {
        return userRepository.findUsersByClassesId(id);
    }
}
