package com.minhdunk.research.mapper;

import com.minhdunk.research.dto.GroupOutputDTO;
import com.minhdunk.research.entity.Group;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupMapper {
    GroupOutputDTO getGroupOutputDTOFromGroup(Group group);
}
