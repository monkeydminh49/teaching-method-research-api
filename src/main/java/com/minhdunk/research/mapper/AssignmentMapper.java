package com.minhdunk.research.mapper;

import com.minhdunk.research.dto.AssignmentInputDTO;
import com.minhdunk.research.dto.AssignmentOutputDTO;
import com.minhdunk.research.entity.Assignment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AssignmentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "classroom", ignore = true)
    @Mapping(target = "assignedDateTime", ignore = true)
    @Mapping(target = "isForGroup", source = "assignmentInputDTO.isForGroup")
    Assignment getAssignmentFromAssignmentInputDTO(AssignmentInputDTO assignmentInputDTO);

    AssignmentOutputDTO getAssignmentOutputDTOFromAssignment(Assignment assignment);

    List<AssignmentOutputDTO> getAssignmentOutputDTOsFromAssignments(List<Assignment> assignments);


}
