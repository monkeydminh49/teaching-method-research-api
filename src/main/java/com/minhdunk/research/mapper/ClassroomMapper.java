package com.minhdunk.research.mapper;

import com.minhdunk.research.dto.ClassroomInputDTO;
import com.minhdunk.research.dto.ClassroomOutputDTO;
import com.minhdunk.research.dto.ClassroomOutputInListDTO;
import com.minhdunk.research.entity.Classroom;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClassroomMapper {

    @Mapping(target = "teacherId", source = "teacher.id")
    @Mapping(target = "teacherFirstName", source = "teacher.firstName")
    @Mapping(target = "teacherLastName", source = "teacher.lastName")
    @Mapping(target = "teacherAvatarId", source = "teacher.avatar.id")
    ClassroomOutputDTO getClassRoomOutputDTOFromClassRoom(Classroom classRoom);

    @Mapping(target = "teacher", ignore = true)
    @Mapping(target = "students", ignore = true)
    @Mapping(target = "numberOfStudents", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    Classroom getClassRoomFromClassRoomInputDTO(ClassroomInputDTO classRoomInputDTO);

    @Mapping(target = "teacherId", source = "teacher.id")
    @Mapping(target = "teacherFirstName", source = "teacher.firstName")
    @Mapping(target = "teacherLastName", source = "teacher.lastName")
    @Mapping(target = "teacherAvatarId", source = "teacher.avatar.id")
    ClassroomOutputInListDTO getClassRoomOutputInListDTOFromClassRoom(Classroom classRoom);

    List<ClassroomOutputDTO> getClassRoomOutputDTOsFromClassRooms(List<Classroom> classRooms);

    List<ClassroomOutputInListDTO> getClassRoomOutputInListDTOsFromClassRooms(List<Classroom> classRooms);
}
