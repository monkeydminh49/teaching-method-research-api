package com.minhdunk.research.service;

import com.minhdunk.research.repository.ChoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChoiceService {
    @Autowired
    private ChoiceRepository choiceRepository;
}
