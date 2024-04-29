package com.minhdunk.research.controller;

import com.minhdunk.research.dto.*;
import com.minhdunk.research.entity.Test;
import com.minhdunk.research.entity.TestHistory;
import com.minhdunk.research.mapper.TestMapper;
import com.minhdunk.research.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@Slf4j
@Controller
@RequestMapping("/api/v1/tests")
public class TestController {
    @Autowired
    private TestService testService;
    @Autowired
    private TestMapper testMapper;

    @PostMapping()
    public BaseResponse createTest(Authentication authentication, @RequestBody TestInputDTO testInputDTO) {
        Test test = testService.createTest(authentication, testInputDTO);
        return new BaseResponse( "ok",  "Create test successfully", testMapper.getTestDTOFromTest(test));

    }

    @GetMapping("/{testId}")
    public TestDTO getTest(@PathVariable Long testId) {
        return testMapper.getTestDTOFromTest(testService.getTestById(testId));
    }

    @DeleteMapping("/{testId}")
    public BaseResponse deleteTest(@PathVariable Long testId) {
        testService.deleteTest(testId);
        return new BaseResponse("ok", "Delete test successfully", null);
    }

    @PostMapping("/{testId}/submit")
    public BaseResponse submitTest(Authentication authentication, @PathVariable Long testId, @RequestBody List<QuestionSubmitDTO> questionSubmitDTO) {
        TestHistory testHistory =  testService.submitTest(testId, questionSubmitDTO, authentication);
        return new BaseResponse("ok", "Submit test successfully", testMapper.getTestHistoryOutputDTOFromTestHistory(testHistory));
    }

    @GetMapping("/{testId}/history/")
    public List<TestHistoryOutputDTO> getTestHistorysByTestId(@PathVariable Long testId) {
        return testMapper.getTestHistoryOutputDTOsFromTestHistorys(testService.getTestHistory(testId));
    }

    @GetMapping("/{testId}/user-history")
    public List<TestHistoryOutputDTO> getUserTestHistoryByTestId(@PathVariable Long testId, Authentication authentication) {
        return testMapper.getTestHistoryOutputDTOsFromTestHistorys(testService.getUserTestHistory(testId, authentication));
    }

    @GetMapping("/{test-id}/user-history/{user-id}")
    public List<TestHistoryOutputDTO> getUserTestHistoryByTestIdAndUserId(@PathVariable("test-id") Long testId, @PathVariable("user-id") Long userId) {
        return testMapper.getTestHistoryOutputDTOsFromTestHistorys(testService.getUserTestHistoryByTestIdAndUserId(testId, userId));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<TestDTO> getAllTests() {
        return testMapper.getTestDTOsFromTests(testService.getAllTests());
    }

    @GetMapping("/history/{testHistoryId}")
    public TestHistoryOutputDTO getTestHistoryByTestHistoryId(@PathVariable Long testHistoryId) {
        return testMapper.getTestHistoryOutputDTOFromTestHistory(testService.getTestHistoryByTestHistoryId(testHistoryId));
    }

    @DeleteMapping("/history/{testHistoryId}")
    public BaseResponse deleteTestHistory(@PathVariable Long testHistoryId) {
        testService.deleteTestHistory(testHistoryId);
        return new BaseResponse("ok", "Delete test history successfully", null);
    }

}
