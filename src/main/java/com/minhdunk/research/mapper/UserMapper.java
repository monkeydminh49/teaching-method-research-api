package com.minhdunk.research.mapper;

import com.minhdunk.research.dto.RegisterRequestDTO;
import com.minhdunk.research.dto.StudentOutputDTO;
import com.minhdunk.research.dto.UserOutputDTO;
import com.minhdunk.research.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "avatarId", source = "avatar.id")
    @Mapping(target = "token", ignore = true)
    UserOutputDTO getUserOutputDTOFromUser(User user);

    @Mapping(target = "groups", ignore = true)
    @Mapping(target = "avatar", ignore = true)
    @Mapping(target = "classes", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "id", ignore = true)
    User getUserFromRegisterRequestDTO(RegisterRequestDTO registerRequestDTO);

    List<UserOutputDTO> getUserOutputDTOsFromUsers(List<User> users);

    @Mapping(target = "avatarId", source = "avatar.id")
    @Mapping(target = "email", source = "email")
    StudentOutputDTO getStudentOutputDTOFromUser(User user);

    List<StudentOutputDTO> getStudentOutputDTOsFromUsers(List<User> users);


}
