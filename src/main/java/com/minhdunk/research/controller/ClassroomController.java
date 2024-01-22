package com.minhdunk.research.controller;

import com.minhdunk.research.dto.*;
import com.minhdunk.research.entity.Assignment;
import com.minhdunk.research.entity.Classroom;
import com.minhdunk.research.entity.Post;
import com.minhdunk.research.entity.User;
import com.minhdunk.research.mapper.*;
import com.minhdunk.research.service.AssignmentService;
import com.minhdunk.research.service.ClassroomService;
import com.minhdunk.research.service.NotificationService;
import com.minhdunk.research.service.PostService;
import com.minhdunk.research.utils.PostType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
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
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private AssignmentMapper assignmentMapper;
    @Autowired
    private AssignmentService  assignmentService;
    @Autowired
    private PostService postService;
    @Autowired
    private PostMapper postMapper;

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
    public Map<String, Object> joinClassroom(@RequestParam("code") String code, Principal principal){
        Classroom classroom = classRoomService.joinClassroom(code, principal);
        ClassroomOutputDTO classroomOutputDTO = classRoomMapper.getClassRoomOutputDTOFromClassRoom(classroom);
        return Map.of("status", "success", "body" , classroomOutputDTO,"message", "Joined classroom " + code + " successfully");
    }

    @GetMapping("/{id}/students")
    public List<StudentOutputDTO> getStudentsByClassroomId(@PathVariable("id") Long id){
        List<User> students = classRoomService.getStudentsByClassroomId(id);
        return userMapper.getStudentOutputDTOsFromUsers(students);
    }

    @GetMapping("/all")
    public List<ClassroomOutputInListDTO> getAllClassrooms(Principal principal) {
        return  classRoomMapper.getClassRoomOutputInListDTOsFromClassRooms(classRoomService.getAllClassrooms(principal));
    }

    @PostMapping("/{id}/notifications")
    public Map<String, String> postNotificationWithClassId(Principal principal,@PathVariable("id") Long id, @RequestBody NotificationInputDTO request){
        notificationService.postNotificationToClassWithId(principal, id, request);
        return Map.of("status", "success","message", "Post notification to class with id = " + id + " successfully");
    }

    @GetMapping("/{id}/notifications")
    public List<NotificationOutputDTO> getAllClassNotificationsByClassId(@PathVariable("id") Long id){
        return notificationMapper.getNotificationOutputDTOsFromNotifications(notificationService.getAllClassNotificationsByClassId(id));
    }

    @PostMapping("/{id}/assignments")
    public Map<String, String> postAssignmentWithClassId(@PathVariable("id") Long id, @RequestBody AssignmentInputDTO request){
        log.info("Is assignment for Group " + request.getIsForGroup());
        assignmentService.postAssignmentToClassWithId(id, request);
        return Map.of("status", "success","message", "Post assignment to class with id = " + id + " successfully");
    }

    @GetMapping("/{id}/assignments")
    public List<AssignmentOutputDTO> getAllClassAssignmentsByClassId(@PathVariable("id") Long id){
        return assignmentMapper.getAssignmentOutputDTOsFromAssignments(assignmentService.getAllClassAssignmentsByClassId(id));
    }

    @GetMapping("/{id}/posts")
    public List<PostOutputDTO> getPostsByClassroomId(@PathVariable Long id) {
        List<Post> posts = postService.getPostsByClassroomId(id);
        return postMapper.getPostOutputDTOsFromPosts(posts);
    }

    @GetMapping("/{id}/author/{authorId}/posts")
    public List<PostOutputDTO> getPostsByClassroomIdAndAuthorId(@PathVariable Long id, @PathVariable Long authorId) {
        List<Post> posts = postService.getPostsByClassroomIdAndAuthorId(id, authorId);
        return postMapper.getPostOutputDTOsFromPosts(posts);
    }

    @GetMapping("/{id}/assignments/status")
    public Map<Object, Object> getAssignmentStatusByClassroomId(@PathVariable Long id) {
        Map<Object,Object> status = new HashMap<>();
        Map<Object, Object> tmpStatus = new HashMap<>();
        List<Assignment> assignments = assignmentService.getAllClassAssignmentsByClassId(id);
        List<UserOutputDTO> students = classRoomService.getStudentOutputDTOsByClassroomId(id);
        List<Post> posts = postService.getPostsByClassroomIdWithoutMedias(id);
        status.put("assignments", assignmentMapper.getAssignmentOutputDTOsFromAssignments(assignments));

        for (UserOutputDTO student: students) {
            tmpStatus.put(student.getId(), new int[assignments.size()]);
        }

        for (int i = 0; i < assignments.size(); i++) {
            Assignment assignment = assignments.get(i);
            for (UserOutputDTO student : students) {
                int[] studentStatus = (int[]) tmpStatus.get(student.getId());
                for (Post post : posts) {
                    if (post.getAuthorId().equals(student.getId()) && post.getAssignment().getId().equals(assignment.getId())) {
                        if (post.getType().equals(PostType.APPROVED)) {
                            studentStatus[i] = 2;
                        } else if (post.getType().equals(PostType.PENDING)) {
                            studentStatus[i] = 1;
                        } else if (post.getType().equals(PostType.REJECTED)) {
                            studentStatus[i] = -1;
                        }
                        break;
                    }
                }
            }
        }


        status.put("status", tmpStatus);
        status.put("students", students);
        status.put("notations", Map.of(
                0, "NOT_YET_SUBMITTED" ,
                1, PostType.PENDING,
                2, PostType.APPROVED,
                -1, PostType.REJECTED
        ));

        return status;

    }



}
