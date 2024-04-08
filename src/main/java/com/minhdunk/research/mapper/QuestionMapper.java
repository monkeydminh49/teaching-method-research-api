package com.minhdunk.research.mapper;

import com.minhdunk.research.dto.QuestionHistoryOutputDTO;
import com.minhdunk.research.entity.Question;
import com.minhdunk.research.entity.QuestionHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    @Mapping(target = "test", ignore = true)
    @Mapping(target = "choices", ignore = true)
    QuestionHistory getQuestionHistoryFromQuestion(Question question);

    @Mapping(target = "testId", source = "test.id")
    QuestionHistoryOutputDTO getQuestionHistoryOutputDTOFromQuestionHistory(QuestionHistory questionHistory);

    List<QuestionHistoryOutputDTO> getQuestionHistoryOutputDTOsFromQuestionHistorys(List<QuestionHistory> questionHistories);
}
