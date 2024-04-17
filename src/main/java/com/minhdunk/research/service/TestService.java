package com.minhdunk.research.service;

import com.minhdunk.research.component.UserInfoUserDetails;
import com.minhdunk.research.dto.ChoiceSubmitDTO;
import com.minhdunk.research.dto.QuestionSubmitDTO;
import com.minhdunk.research.dto.TestInputDTO;
import com.minhdunk.research.entity.*;
import com.minhdunk.research.exception.NotFoundException;
import com.minhdunk.research.exception.TestTypeExistsForDocumentException;
import com.minhdunk.research.mapper.ChoiceMapper;
import com.minhdunk.research.mapper.QuestionMapper;
import com.minhdunk.research.mapper.TestMapper;
import com.minhdunk.research.repository.*;
import com.minhdunk.research.utils.HintType;
import com.minhdunk.research.utils.QuestionType;
import com.minhdunk.research.utils.TestType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TestService {
    @Autowired
    private TestRepository testRepository;
    @Autowired
    private TestMapper testMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private ChoiceMapper choiceMapper;
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private ChoiceRepository choiceRepository;
    @Autowired
    private HintRepository hintRepository;
    @Autowired
    private TestHistoryRepository testHistoryRepository;
    @Autowired
    private QuestionHistoryRepository questionHistoryRepository;
    @Autowired
    private ChoiceHistoryRepository choiceHistoryRepository;


    @Transactional
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

    public Test getTestByDocumentIdAndType(Long documentId, TestType type) {
        Optional<Test> test =  testRepository.findByDocumentIdAndType(documentId, type);
        return test.orElseThrow(() -> new NotFoundException("Test not found"));
    }

    @Transactional
    public TestHistory submitTest(Long testId, List<QuestionSubmitDTO> questionSubmitDTO, Authentication authentication) {
        UserInfoUserDetails userInfoUserDetails = (UserInfoUserDetails) authentication.getPrincipal();
        User user = userInfoUserDetails.getUser();

        Test test = testRepository.findById(testId).orElseThrow(() -> new NotFoundException("Test not found"));

        TestHistory testHistory = testMapper.getTestHistoryFromTest(test);
        testHistory.setSubmitter(user);
        testHistory.setSubmitAt(LocalDateTime.now());
        testHistory.setTest(test);

        int totalNumberOfQuestions = test.getQuestions().size();

        List<Question> questions = test.getQuestions();

        int countTotalNumberOfCorrectQuestions = 0;
        for (QuestionSubmitDTO questionSubmit : questionSubmitDTO) {
//            Question question = questionRepository.findById(questionSubmit.getQuestionId()).orElseThrow(() -> new NotFoundException("Question not found"));
            Question question = questions.stream().filter(q -> q.getId().equals(questionSubmit.getQuestionId())).findFirst().orElseThrow(() -> new NotFoundException("Question not found"));

            boolean isACorrectQuestion = true;

            if (question.getType() == QuestionType.FILL_IN_THE_BLANK){
                totalNumberOfQuestions--;
                continue;
            }

            for (Choice choice : question.getChoices()) {
                for (ChoiceSubmitDTO choiceSubmit : questionSubmit.getChoices()) {
                    if (choice.getId().equals(choiceSubmit.getChoiceId())) {
                        if (!choice.getIsAnswer().equals(choiceSubmit.getIsPicked())) {
                            isACorrectQuestion = false;
                        }
                    }
                }
            }
            if (isACorrectQuestion) {
                countTotalNumberOfCorrectQuestions++;
            }
        }

        double score = (double) countTotalNumberOfCorrectQuestions / totalNumberOfQuestions * 10;

        testHistory.setTotalScore(score);
//        TestHistory savedTest = testHistoryRepository.save(testHistory);

        List<QuestionHistory> questionHistories = new ArrayList<>();

        for (QuestionSubmitDTO questionSubmit : questionSubmitDTO) {
//            Question question = questionRepository.findById(questionSubmit.getQuestionId()).orElseThrow(() -> new NotFoundException("Question not found"));
            Question question = questions.stream().filter(q -> q.getId().equals(questionSubmit.getQuestionId())).findFirst().orElseThrow(() -> new NotFoundException("Question not found"));

            QuestionHistory questionHistory = questionMapper.getQuestionHistoryFromQuestion(question);
            questionHistory.setTest(testHistory);

//            QuestionHistory questionHistory1 = questionHistoryRepository.save(questionHistory);

            List<ChoiceHistory> choiceHistories = new ArrayList<>();
            for (Choice choice : question.getChoices()) {
                for (ChoiceSubmitDTO choiceSubmit : questionSubmit.getChoices()) {
                    if (choice.getId().equals(choiceSubmit.getChoiceId())) {

                        ChoiceHistory choiceHistory = choiceMapper.getChoiceHistoryFromChoice(choice);

                        if (question.getType() == QuestionType.FILL_IN_THE_BLANK) {
                            choiceHistory.setContent(choiceSubmit.getContent());
                            choiceHistory.setIsPicked(choiceSubmit.getIsPicked());
                            choiceHistory.setQuestion(questionHistory);
                            choiceHistory.setTest(testHistory);
                            choiceHistories.add(choiceHistory);
                            continue;
                        }

                        choiceHistory.setIsPicked(choiceSubmit.getIsPicked());
                        choiceHistory.setQuestion(questionHistory);
                        choiceHistory.setTest(testHistory);
                        choiceHistories.add(choiceHistory);
//                        choiceHistoryRepository.save(choiceHistory);
                    }
                }
            }

            List<HintHistory> hintHistories = new ArrayList<>();
            for (Hint hint : question.getAnswerHints()) {
                HintHistory hintHistory = new HintHistory();
                hintHistory.setContent(hint.getContent());
                hintHistory.setType(hint.getType());
                hintHistory.setQuestionHistory(questionHistory);
                hintHistories.add(hintHistory);
            }

            questionHistory.setChoices(choiceHistories);
            questionHistory.setAnswerHintHistories(hintHistories);
            questionHistories.add(questionHistory);

        }

        testHistory.setQuestions(questionHistories);

        return testHistoryRepository.save(testHistory);

    }

    public List<TestHistory> getTestHistory(Long testId) {
        return testHistoryRepository.findByTestId(testId);
    }

    public List<TestHistory> getUserTestHistory(Long testId, Authentication authentication) {
        UserInfoUserDetails userInfoUserDetails = (UserInfoUserDetails) authentication.getPrincipal();
        User user = userInfoUserDetails.getUser();
        return testHistoryRepository.findByTestIdAndSubmitterId(testId, user.getId());
    }

    @Transactional
    public List<Test> getAllTests() {
        return testRepository.findAll();
    }

    public TestHistory getTestHistoryByTestHistoryId(Long testHistoryId) {
        return testHistoryRepository.findById(testHistoryId).orElseThrow(() -> new NotFoundException("Test history not found"));
    }

    public void deleteTestHistory(Long testHistoryId) {
        testHistoryRepository.deleteById(testHistoryId);
    }

    public List<Test> getAllTestsByDocumentId(Long documentId) {
        return testRepository.findAllByDocumentId(documentId);
    }
}
