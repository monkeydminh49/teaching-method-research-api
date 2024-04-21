package com.minhdunk.research.controller;

import com.minhdunk.research.dto.CounsellingOutputDTO;
import com.minhdunk.research.mapper.CounsellingMapper;
import com.minhdunk.research.service.CounsellingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/counsellings")
public class CounsellingController {
    @Autowired
    private CounsellingService counsellingService;

    @Autowired
    private CounsellingMapper counsellingMapper;

    @GetMapping("/{id}")
    public CounsellingOutputDTO getCounsellingById(@PathVariable Long id) {
        return counsellingMapper.getCounsellingOutputDtoFromCounselling(counsellingService.getCounsellingById(id));
    }

}
