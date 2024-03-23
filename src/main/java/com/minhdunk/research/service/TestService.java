package com.minhdunk.research.service;

import com.minhdunk.research.component.UserInfoUserDetails;
import com.minhdunk.research.dto.TestInputDTO;
import com.minhdunk.research.entity.Document;
import com.minhdunk.research.entity.Question;
import com.minhdunk.research.entity.Test;
import com.minhdunk.research.entity.User;
import com.minhdunk.research.exception.NotFoundException;
import com.minhdunk.research.exception.TestTypeExistsForDocumentException;
import com.minhdunk.research.mapper.TestMapper;
import com.minhdunk.research.repository.*;
import com.minhdunk.research.utils.HintType;
import com.minhdunk.research.utils.TestType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    @Autowired
    private HintRepository hintRepository;

    public Test createTest(Authentication authentication, TestInputDTO testInputDTO) {
        Optional<Test> checkTest = testRepository.findByDocumentIdAndType(testInputDTO.getDocumentId(), testInputDTO.getType());

        if (checkTest.isPresent()) {
            throw new TestTypeExistsForDocumentException("Test type exists for this document. Please delete the existing test first");
        }

        Test test = testMapper.getTestFromTestInputDTO(testInputDTO);
        UserInfoUserDetails userInfoUserDetails = (UserInfoUserDetails) authentication.getPrincipal();
        User author = userInfoUserDetails.getUser();
        test.setAuthor(author);
        Document document = documentRepository.findById(testInputDTO.getDocumentId()).orElseThrow(() -> new NotFoundException("Document not found"));
        document.getTests().add(test);
        documentRepository.save(document);
        test.setDocument(document);
        Test savedTest =  testRepository.save(test);

        savedTest.getQuestions().forEach(question -> {
            question.setTest(savedTest);
            Question savedQuestion = questionRepository.save(question);

            question.getChoices().forEach(choice -> {
                choice.setQuestion(savedQuestion);
                choiceRepository.save(choice);
            });

            question.getHints().forEach(hint -> {
                hint.setType(HintType.HINT_REGULAR);
                hint.setQuestion(savedQuestion);
                hintRepository.save(hint);
            });

            question.getAnswerHints().forEach(hint -> {
                hint.setType(HintType.HINT_ANSWER);
                hint.setQuestion(savedQuestion);
                hintRepository.save(hint);
            });

        });

        return savedTest;


    }



    public Test getTestById(Long testId) {
        Test test = testRepository.findById(testId).orElseThrow(() -> new NotFoundException("Test not found"));
//        List<Question> questions = questionRepository.findByTestId(testId);
//        test.setQuestions(questions);
        return test;
    }

    public void deleteTest(Long testId) {
        testRepository.deleteById(testId);
    }

    public Test getTestsByDocumentIdAndType(Long documentId, TestType type) {
        Optional<Test> test =  testRepository.findByDocumentIdAndType(documentId, type);
        return test.orElseThrow(() -> new NotFoundException("Test not found"));
    }
}
