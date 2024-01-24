package com.minhdunk.research.controller;

import com.minhdunk.research.dto.GroupOutputDTO;
import com.minhdunk.research.mapper.GroupMapper;
import com.minhdunk.research.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Controller
@RestController
@RequestMapping("/api/v1/groups")
public class GroupController {
    @Autowired
    private GroupService groupService;
    @Autowired
    private GroupMapper groupMapper;
    @GetMapping("/{id}")
    public GroupOutputDTO getGroupById(@PathVariable("id") Long id){
        return groupMapper.getGroupOutputDTOFromGroup(groupService.getGroupById(id));
    }
}
