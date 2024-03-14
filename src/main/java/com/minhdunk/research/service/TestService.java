package com.minhdunk.research.service;

import com.minhdunk.research.component.UserInfoUserDetails;
import com.minhdunk.research.dto.TestInputDTO;
import com.minhdunk.research.entity.Document;
import com.minhdunk.research.entity.Question;
import com.minhdunk.research.entity.Test;
import com.minhdunk.research.entity.User;
import com.minhdunk.research.exception.NotFoundException;
import com.minhdunk.research.mapper.TestMapper;
import com.minhdunk.research.repository.ChoiceRepository;
import com.minhdunk.research.repository.DocumentRepository;
import com.minhdunk.research.repository.QuestionRepository;
import com.minhdunk.research.repository.TestRepository;
import com.minhdunk.research.utils.TestType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {
    @Autowired
    private TestRepository testRepository;
    @Autowired
    private TestMapper testMapper;
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private ChoiceRepository choiceRepository;

    public Test createTest(Authentication authentication, TestInputDTO testInputDTO) {
        Test test = testMapper.getTestFromTestInputDTO(testInputDTO);
        UserInfoUserDetails userInfoUserDetails = (UserInfoUserDetails) authentication.getPrincipal();
        User author = userInfoUserDetails.getUser();
        test.setAuthor(author);
        Document document = documentRepository.findById(testInputDTO.getDocumentId()).orElseThrow(() -> new NotFoundException("Document not found"));
        test.setDocument(document);
        Test savedTest =  testRepository.save(test);

        savedTest.getQuestions().forEach(question -> {
            question.setTest(savedTest);
            Question savedQuestion = questionRepository.save(question);

            question.getChoices().forEach(choice -> {
                choice.setQuestion(savedQuestion);
                choiceRepository.save(choice);
            });

        });

        return savedTest;


    }



    public Test getTestById(Long testId) {
        return testRepository.findById(testId).orElseThrow(() -> new NotFoundException("Test not found"));
    }

    public void deleteTest(Long testId) {
        testRepository.deleteById(testId);
    }

    public List<Test> getTestsByDocumentId(Long documentId, TestType type) {

        return testRepository.findByDocumentId(documentId, type);
    }
}
