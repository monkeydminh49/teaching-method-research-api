package com.minhdunk.research.service;

import com.minhdunk.research.dto.ClassroomInputDTO;
import com.minhdunk.research.entity.Classroom;
import com.minhdunk.research.entity.User;
import com.minhdunk.research.exception.NotFoundException;
import com.minhdunk.research.mapper.ClassroomMapper;
import com.minhdunk.research.repository.ClassroomRepository;
import com.minhdunk.research.repository.UserRepository;
import com.minhdunk.research.utils.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
public class ClassroomService {
    @Autowired
    private ClassroomRepository classroomRepository;
    @Autowired
    private ClassroomMapper classRoomMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClassroomCodeGenerator classroomCodeGenerator;
    public Classroom createClassRoom(Principal principal, ClassroomInputDTO request) {
        Classroom classRoom = classRoomMapper.getClassRoomFromClassRoomInputDTO(request);
        classRoom.setStudents(new HashSet<>());
        // Set teacher for classroom
        User teacher = userRepository.findByUsername(principal.getName()).get();

        // Generate unique code with numbers and characters for classroom
        String code = classroomCodeGenerator.generateUniqueClassroomCode();
        classRoom.setCode(code);
        classRoom.setTeacher(teacher);
        return classroomRepository.save(classRoom);
    }

    public Classroom getClassroomById(Long id) {
        Classroom classroom = classroomRepository.findById(id).orElseThrow(() -> new NotFoundException("Classroom not found"));
        log.info(String.valueOf(classroom.getStudents().size()));
        return classroom;
    }

    public Classroom getClassroomByCode(String code) {
        return classroomRepository.findByCode(code).orElseThrow(() -> new NotFoundException("Classroom not found"));
    }

    public Classroom joinClassroom(String code, Principal principal) {
        Classroom classroom = getClassroomByCode(code);
        User student = userRepository.findByUsername(principal.getName()).get();
        classroom.addStudent(student);
        classroom.setNumberOfStudents(classroom.getStudents().size());
        return classroomRepository.save(classroom);
    }

    public List<User> getStudentsByClassroomId(Long id) {
        return userRepository.findUsersByClassesId(id);
    }

    public List<Classroom> getAllClassrooms(Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).get();
        if (user.getRole() == UserRole.ROLE_TEACHER) {
            return classroomRepository.findAllByTeacherId(user.getId());
        }
        return classroomRepository.getListClassroomsByStudentId(user.getId());
    }
}
