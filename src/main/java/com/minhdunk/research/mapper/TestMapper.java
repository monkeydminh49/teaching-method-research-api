package com.minhdunk.research.mapper;

import com.minhdunk.research.dto.TestDTO;
import com.minhdunk.research.dto.TestInputDTO;
import com.minhdunk.research.dto.TestWithoutAnswerDTO;
import com.minhdunk.research.entity.Test;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TestMapper {
    @Mapping(target = "documentId", source = "document.id")
    @Mapping(target = "authorId", source = "author.id")
    TestDTO getTestDTOFromTest(Test test);

    @Mapping(target = "documentId", source = "document.id")
    @Mapping(target = "authorId", source = "author.id")
    TestWithoutAnswerDTO getTestWithoutAnswerDTOFromTest(Test test);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "document", ignore = true)
    @Mapping(target = "author", ignore = true)
    Test getTestFromTestInputDTO(TestInputDTO testInputDTO);

    List<TestDTO> getTestDTOsFromTests(List<Test> testsByDocumentId);
}
