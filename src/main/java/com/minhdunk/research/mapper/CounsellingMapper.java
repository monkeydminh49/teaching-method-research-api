package com.minhdunk.research.mapper;

//import com.minhdunk.research.dto.CounsellingInputDTO;
//import com.minhdunk.research.entity.Counselling;
import com.minhdunk.research.dto.CounsellingInputDTO;
import com.minhdunk.research.dto.CounsellingOutputDTO;
import com.minhdunk.research.entity.Counselling;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CounsellingMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "document", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    Counselling getCounsellingFromCounsellingInputDto(CounsellingInputDTO counsellingInputDTO);

    @Mapping(target = "documentId", source = "document.id")
    CounsellingOutputDTO getCounsellingOutputDtoFromCounselling(Counselling counselling);

    List<CounsellingOutputDTO> getCounsellingOutputDtosFromCounsellings(List<Counselling> counsellings);
}
