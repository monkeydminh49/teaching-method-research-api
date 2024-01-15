package com.minhdunk.research.controller;

import com.minhdunk.research.dto.ClassroomInputDTO;
import com.minhdunk.research.dto.ClassroomOutputDTO;
import com.minhdunk.research.entity.Classroom;
import com.minhdunk.research.mapper.ClassroomMapper;
import com.minhdunk.research.service.ClassroomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin
@Controller
@RestController
@RequestMapping("/api/v1/classrooms")
@Slf4j
public class ClassroomController {
    @Autowired
    private ClassroomService classRoomService;
    @Autowired
    private ClassroomMapper classRoomMapper;
    @PostMapping()
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ClassroomOutputDTO createClassRoom(Principal principal, @RequestBody ClassroomInputDTO request) {
        Classroom newClassroom = classRoomService.createClassRoom(principal, request);
        return classRoomMapper.getClassRoomOutputDTOFromClassRoom(newClassroom);
    }
}
