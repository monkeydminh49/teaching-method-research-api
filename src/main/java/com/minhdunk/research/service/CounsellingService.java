package com.minhdunk.research.service;

import com.minhdunk.research.entity.Counselling;
import com.minhdunk.research.repository.CounsellingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CounsellingService {
    @Autowired
    private CounsellingRepository counsellingRepository;

    public Counselling getCounsellingById(Long id) {
        return counsellingRepository.findById(id).orElseThrow(() -> new RuntimeException("Counselling not found"));
    }
}
