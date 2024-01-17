package com.minhdunk.research.service;

import com.minhdunk.research.dto.AssignmentInputDTO;
import com.minhdunk.research.entity.Assignment;
import com.minhdunk.research.entity.Classroom;
import com.minhdunk.research.exception.NotFoundException;
import com.minhdunk.research.mapper.AssignmentMapper;
import com.minhdunk.research.repository.AssignmentRepository;
import com.minhdunk.research.repository.ClassroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AssignmentService {
    @Autowired
    private AssignmentRepository assignmentRepository;
    @Autowired
    private AssignmentMapper assignmentMapper;
    @Autowired
    private ClassroomRepository classroomRepository;

    public List<Assignment> getAllClassAssignmentsByClassId(Long id) {
        return assignmentRepository.findAllByClassroomId(id);
    }

    public void postAssignmentToClassWithId(Long id, AssignmentInputDTO request) {
        Assignment assignment = assignmentMapper.getAssignmentFromAssignmentInputDTO(request);
        Classroom classroom = classroomRepository.findById(id).orElseThrow(()-> new NotFoundException("Class id "+ id + " not found"));
        assignment.setClassroom(classroom);
        assignment.setAssignedDateTime(LocalDateTime.now());
        assignmentRepository.save(assignment);
    }

    public  Assignment getAssignmentById(Long id) {
        return assignmentRepository.findById(id).orElseThrow(()-> new NotFoundException("Assignment id "+ id + " not found"));
    }
}
