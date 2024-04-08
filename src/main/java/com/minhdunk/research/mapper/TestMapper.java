package com.minhdunk.research.mapper;

import com.minhdunk.research.dto.*;
import com.minhdunk.research.entity.ChoiceHistory;
import com.minhdunk.research.entity.QuestionHistory;
import com.minhdunk.research.entity.Test;
import com.minhdunk.research.entity.TestHistory;
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

    @Mapping(target = "totalScore", ignore = true)
    @Mapping(target = "test", ignore = true)
    @Mapping(target = "submitter", ignore = true)
    @Mapping(target = "submitAt", ignore = true)
    @Mapping(target = "questions", ignore = true)
    TestHistory getTestHistoryFromTest(Test test);


    @Mapping(target = "testId", source = "test.id")
    @Mapping(target = "questionId", source = "question.id")
    ChoiceHistoryOutputDTO getChoiceHistoryOutputDTOFromChoiceHistory(ChoiceHistory choiceHistory);

    List<ChoiceHistoryOutputDTO> getChoiceHistoryOutputDTOsFromChoiceHistorys(List<ChoiceHistory> choiceHistories);

    @Mapping(target = "testId", source = "test.id")
    QuestionHistoryOutputDTO getQuestionHistoryOutputDTOFromQuestionHistory(QuestionHistory questionHistory);

    List<QuestionHistoryOutputDTO> getQuestionHistoryOutputDTOsFromQuestionHistorys(List<QuestionHistory> questionHistories);
    @Mapping(target = "testId", source = "test.id")
    @Mapping(target = "submitterId", source = "submitter.id")
    TestHistoryOutputDTO getTestHistoryOutputDTOFromTestHistory(TestHistory testHistory);

    List<TestHistoryOutputDTO> getTestHistoryOutputDTOsFromTestHistorys(List<TestHistory> testHistories);
}
