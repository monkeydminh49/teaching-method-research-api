package com.minhdunk.research.service;

import com.minhdunk.research.dto.ClassroomInputDTO;
import com.minhdunk.research.dto.UserOutputDTO;
import com.minhdunk.research.entity.Assignment;
import com.minhdunk.research.entity.Classroom;
import com.minhdunk.research.entity.Post;
import com.minhdunk.research.entity.User;
import com.minhdunk.research.exception.NotFoundException;
import com.minhdunk.research.mapper.AssignmentMapper;
import com.minhdunk.research.mapper.ClassroomMapper;
import com.minhdunk.research.repository.ClassroomRepository;
import com.minhdunk.research.repository.PostRepository;
import com.minhdunk.research.repository.UserRepository;
import com.minhdunk.research.utils.PostType;
import com.minhdunk.research.utils.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private AssignmentService assignmentService;
    @Autowired
    private AssignmentMapper assignmentMapper;
    @Autowired
    private PostRepository postRepository;

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

    public List<UserOutputDTO> getStudentOutputDTOsByClassroomId(Long id) {
        return userRepository.getUserOutputDTOsByClassesId(id);
    }

    public Map<Object, Object> getAssignmentStatusByClassroomId(Long id) {
        Map<Object,Object> status = new HashMap<>();
        Map<Object, Object> tmpStatus = new HashMap<>();
        List<Assignment> assignments = assignmentService.getAllClassAssignmentsByClassId(id);
        List<UserOutputDTO> students = getStudentOutputDTOsByClassroomId(id);
        List<Post> posts = postRepository.getPostsByClassroomIdWithoutMedias(id);
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
