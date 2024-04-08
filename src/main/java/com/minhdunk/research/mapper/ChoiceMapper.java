package com.minhdunk.research.mapper;

import com.minhdunk.research.dto.ChoiceHistoryOutputDTO;
import com.minhdunk.research.entity.Choice;
import com.minhdunk.research.entity.ChoiceHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChoiceMapper {
    @Mapping(target = "isPicked", ignore = true)
    @Mapping(target = "question", ignore = true)
    @Mapping(target = "test", ignore = true)
    ChoiceHistory getChoiceHistoryFromChoice(Choice choice);

    @Mapping(target = "testId", source = "test.id")
    @Mapping(target = "questionId", source = "question.id")
    ChoiceHistoryOutputDTO getChoiceHistoryOutputDTOFromChoiceHistory(ChoiceHistory choiceHistory);

    List<ChoiceHistoryOutputDTO> getChoiceHistoryOutputDTOsFromChoiceHistorys(List<ChoiceHistory> choiceHistories);
}
