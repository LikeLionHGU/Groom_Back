package com.example.churchback2024.controller;

import com.example.churchback2024.controller.request.group.GroupCreateRequest;
import com.example.churchback2024.controller.response.group.GroupListResponse;
import com.example.churchback2024.domain.GroupC;
import com.example.churchback2024.dto.GroupDto;
import com.example.churchback2024.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/church+/group")
public class GroupController {
    private final GroupService groupService;

    @GetMapping("/list")
    public ResponseEntity<GroupListResponse> getGroupList(){
        GroupListResponse groupListResponse = groupService.getGroupList();
        return ResponseEntity.ok(groupListResponse);
    }

    @PostMapping("/create")
    public void createGroup(@RequestBody GroupCreateRequest request){
        groupService.createGroup(GroupDto.from(request));
    }

    @PatchMapping("/{groupId}")
    public ResponseEntity<GroupC> updateGroup(@PathVariable Long groupId, @RequestBody GroupCreateRequest request){
        return ResponseEntity.ok(groupService.updateGroup(groupId, GroupDto.from(request)));
    }

    @DeleteMapping("/{groupId}")
    public void deleteGroup(@PathVariable Long groupId){
        groupService.deleteGroup(groupId);
    }
}