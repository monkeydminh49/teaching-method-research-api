package com.minhdunk.research.service;

import com.minhdunk.research.dto.ClassroomInputDTO;
import com.minhdunk.research.entity.Classroom;
import com.minhdunk.research.entity.User;
import com.minhdunk.research.mapper.ClassroomMapper;
import com.minhdunk.research.repository.ClassroomRepository;
import com.minhdunk.research.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

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
}
