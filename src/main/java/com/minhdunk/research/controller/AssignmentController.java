package com.minhdunk.research.controller;

import com.minhdunk.research.dto.AssignmentOutputDTO;
import com.minhdunk.research.mapper.AssignmentMapper;
import com.minhdunk.research.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Controller
@RestController
@RequestMapping("/api/v1/assignments")
public class AssignmentController {
    @Autowired
    private AssignmentService assignmentService;
    @Autowired
    private AssignmentMapper assignmentMapper;

    @GetMapping("/{id}")
    public AssignmentOutputDTO getAssignmentById(@PathVariable("id") Long id){
        return assignmentMapper.getAssignmentOutputDTOFromAssignment(assignmentService.getAssignmentById(id));
    }
}
