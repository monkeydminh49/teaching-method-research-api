package com.minhdunk.research.service;

import com.minhdunk.research.dto.AssignmentInputDTO;
import com.minhdunk.research.dto.AssignmentStatusOutputDTO;
import com.minhdunk.research.entity.Assignment;
import com.minhdunk.research.entity.Classroom;
import com.minhdunk.research.entity.Document;
import com.minhdunk.research.exception.NotFoundException;
import com.minhdunk.research.mapper.AssignmentMapper;
import com.minhdunk.research.repository.AssignmentRepository;
import com.minhdunk.research.repository.ClassroomRepository;
import com.minhdunk.research.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class AssignmentService {
    @Autowired
    private AssignmentRepository assignmentRepository;
    @Autowired
    private AssignmentMapper assignmentMapper;
    @Autowired
    private ClassroomRepository classroomRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private DocumentRepository documentRepository;

    public List<Assignment> getAllClassAssignmentsByClassId(Long id) {
        return assignmentRepository.findAllByClassroomId(id);
    }

    public List<AssignmentStatusOutputDTO> getAllClassAssignmentsStatusByClassId(Principal principal, Long id) {
        Long userId = userService.getUserByUsername(principal.getName()).getId();
        return assignmentRepository.findAllAssignmentsStatusByClassroomId(id, userId);
    }

    @Transactional
    public void postAssignmentToClassWithId(Long id, AssignmentInputDTO request) {
        Document document = documentRepository.findById(request.getDocumentId()).orElseThrow(()-> new NotFoundException("Document id "+ request.getDocumentId() + " not found"));
        Assignment assignment = assignmentMapper.getAssignmentFromAssignmentInputDTO(request);
        Classroom classroom = classroomRepository.findById(id).orElseThrow(()-> new NotFoundException("Class id "+ id + " not found"));
        assignment.setRelatedDocument(document);
        assignment.setClassroom(classroom);
        assignment.setAssignedDateTime(LocalDateTime.now());
        assignmentRepository.save(assignment);
    }

    public  Assignment getAssignmentById(Long id) {
        return assignmentRepository.findById(id).orElseThrow(()-> new NotFoundException("Assignment id "+ id + " not found"));
    }
}
