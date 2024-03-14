package com.minhdunk.research.controller;

import com.minhdunk.research.dto.BaseResponse;
import com.minhdunk.research.dto.TestDTO;
import com.minhdunk.research.dto.TestInputDTO;
import com.minhdunk.research.dto.TestWithoutAnswerDTO;
import com.minhdunk.research.entity.Test;
import com.minhdunk.research.mapper.TestMapper;
import com.minhdunk.research.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
}
