package com.minhdunk.research.controller;

import com.minhdunk.research.dto.ClassroomInputDTO;
import com.minhdunk.research.dto.ClassroomOutputDTO;
import com.minhdunk.research.dto.StudentOutputDTO;
import com.minhdunk.research.dto.UserOutputDTO;
import com.minhdunk.research.entity.Classroom;
import com.minhdunk.research.entity.User;
import com.minhdunk.research.mapper.ClassroomMapper;
import com.minhdunk.research.mapper.UserMapper;
import com.minhdunk.research.service.ClassroomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private UserMapper userMapper;
    @PostMapping()
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ClassroomOutputDTO createClassRoom(Principal principal, @RequestBody ClassroomInputDTO request) {
        Classroom newClassroom = classRoomService.createClassRoom(principal, request);
        return classRoomMapper.getClassRoomOutputDTOFromClassRoom(newClassroom);
    }

    @GetMapping("/{id}")
    public ClassroomOutputDTO getClassroomInfoById(@PathVariable("id") Long id){
        return classRoomMapper.getClassRoomOutputDTOFromClassRoom(classRoomService.getClassroomById(id));

    }

    @GetMapping()
    public ClassroomOutputDTO getClassInfoByCode(@RequestParam("code") String code){
        return classRoomMapper.getClassRoomOutputDTOFromClassRoom(classRoomService.getClassroomByCode(code));
    }

    @PostMapping("/join")
    public Map<String, String> joinClassroom(@RequestParam("code") String code, Principal principal){
        classRoomService.joinClassroom(code, principal);
        return Map.of("status", "success","message", "Joined classroom " + code + " successfully");
    }

    @GetMapping("{id}/students")
    public List<StudentOutputDTO> getStudentsByClassroomId(@PathVariable("id") Long id){
        List<User> students = classRoomService.getStudentsByClassroomId(id);
        return userMapper.getStudentOutputDTOsFromUsers(students);
    }


}
