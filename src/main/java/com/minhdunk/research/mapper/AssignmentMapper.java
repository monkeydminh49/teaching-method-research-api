package com.minhdunk.research.mapper;

import com.minhdunk.research.dto.AssignmentInputDTO;
import com.minhdunk.research.dto.AssignmentOutputDTO;
import com.minhdunk.research.entity.Assignment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AssignmentMapper {
    @Mapping(target = "relatedTest", ignore = true)
    @Mapping(target = "relatedDocument", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "classroom", ignore = true)
    @Mapping(target = "assignedDateTime", ignore = true)
    @Mapping(target = "isForGroup", source = "assignmentInputDTO.isForGroup", defaultExpression = "java(false)")
    Assignment getAssignmentFromAssignmentInputDTO(AssignmentInputDTO assignmentInputDTO);

    @Mapping(target = "relatedTestId", source = "relatedTest.id")
    @Mapping(target = "relatedDocumentId", source = "relatedDocument.id")
    AssignmentOutputDTO getAssignmentOutputDTOFromAssignment(Assignment assignment);

    List<AssignmentOutputDTO> getAssignmentOutputDTOsFromAssignments(List<Assignment> assignments);


}
