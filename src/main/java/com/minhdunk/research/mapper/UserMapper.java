package com.minhdunk.research.mapper;

import com.minhdunk.research.dto.RegisterRequestDTO;
import com.minhdunk.research.dto.UserOutputDTO;
import com.minhdunk.research.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "token", ignore = true)
    UserOutputDTO getUserOutputDTOFromUser(User user);

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "id", ignore = true)
    User getUserFromRegisterRequestDTO(RegisterRequestDTO registerRequestDTO);
}
