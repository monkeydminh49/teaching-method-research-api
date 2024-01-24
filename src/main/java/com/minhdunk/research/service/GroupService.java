package com.minhdunk.research.service;

import com.minhdunk.research.entity.Group;
import com.minhdunk.research.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;
    public Group getGroupById(Long id) {
        return groupRepository.findById(id).orElseThrow(()-> new RuntimeException("Group id not found"));
    }
}
