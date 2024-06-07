package com.example.churchback2024.controller;

import com.example.churchback2024.controller.request.group.GroupAddRequest;
import com.example.churchback2024.controller.request.group.GroupCreateRequest;
import com.example.churchback2024.controller.request.group.GroupMemberUpdateRequest;
import com.example.churchback2024.controller.request.group.GroupUpdateRequest;
import com.example.churchback2024.controller.response.group.GroupListResponse;
import com.example.churchback2024.controller.response.group.GroupResponse;
import com.example.churchback2024.controller.response.group.MemberGroupListResponse;
import com.example.churchback2024.controller.response.member.MemberInfoResponse;
import com.example.churchback2024.dto.GroupDto;
import com.example.churchback2024.dto.MemberGroupDto;
import com.example.churchback2024.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/church+/group")
public class GroupController {
    private final GroupService groupService;

    @GetMapping("/list")
    public ResponseEntity<GroupListResponse> getGroupList(){
        List<GroupDto> dtoList = groupService.getGroupList();
        GroupListResponse groupListResponse = new GroupListResponse(dtoList);
        return ResponseEntity.ok(groupListResponse);
    }
    @GetMapping("/{groupId}")
    public ResponseEntity<GroupResponse> getGroupInfoByGroupId(@PathVariable Long groupId){
        GroupDto groupDto = groupService.getGroupInfo(groupId);
        GroupResponse groupResponse = new GroupResponse(groupDto);
        return ResponseEntity.ok(groupResponse);
    }

    @GetMapping("/list/member/{memberId}")
    public ResponseEntity<GroupListResponse> getGroupListAddMember(@PathVariable Long memberId){
        List<GroupDto> dtoList = groupService.getGroupListByMemberId(memberId);
        GroupListResponse groupListResponse = new GroupListResponse(dtoList);
        return ResponseEntity.ok(groupListResponse);
    }
    @GetMapping("/list/group/{groupId}")
    public ResponseEntity<MemberGroupListResponse> getGroupInfo(@PathVariable Long groupId){
        List<MemberGroupDto> dtoList = groupService.getGroupInfoListByGroupId(groupId);
        MemberGroupListResponse memberGroupListResponse = new MemberGroupListResponse(dtoList);
        return ResponseEntity.ok(memberGroupListResponse);
    }
    @GetMapping("/{groupId}/{memberId}")
    public ResponseEntity<MemberInfoResponse> findInfoByMemberGroup(@PathVariable Long groupId, @PathVariable Long memberId){
        GroupDto groupDto = groupService.getGroupInfo(groupId, memberId);
        MemberInfoResponse memberInfoResponse = new MemberInfoResponse(groupDto);
        return ResponseEntity.ok(memberInfoResponse);
    }
    @PatchMapping("/update/{groupId}/{memberId}")
    public ResponseEntity<GroupResponse> updateMemberGroup(@PathVariable Long groupId, @PathVariable Long memberId, @RequestBody GroupMemberUpdateRequest groupMemberUpdateRequest){
        GroupDto groupDto = groupService.updateMemberGroup(groupId, memberId, groupMemberUpdateRequest);
        GroupResponse groupResponse = new GroupResponse(groupDto);
        return ResponseEntity.ok(groupResponse);
    }

    @PostMapping("/create")
    public ResponseEntity<GroupResponse> createGroup(@RequestBody GroupCreateRequest request){
        GroupDto groupDto = groupService.createGroup(GroupDto.from(request));
        GroupResponse groupResponse = new GroupResponse(groupDto);
        return ResponseEntity.ok(groupResponse);
    }

    @PostMapping("/add")
    public ResponseEntity<GroupResponse> addGroup(@RequestBody GroupAddRequest request){
        GroupDto groupDto = groupService.addGroup(GroupDto.from(request));
        GroupResponse groupResponse = new GroupResponse(groupDto);
        return ResponseEntity.ok(groupResponse);
    }
    @PatchMapping("/{groupId}")
    public ResponseEntity<GroupResponse> updateGroup(@PathVariable Long groupId, @ModelAttribute GroupUpdateRequest request, @RequestParam(value="groupImage", required = false) MultipartFile groupImage) throws IOException {
        return ResponseEntity.ok(groupService.updateGroup(groupId, GroupDto.from(request), groupImage));
    }

    @DeleteMapping("/{groupId}")
    public void deleteGroup(@PathVariable Long groupId){
        groupService.deleteGroup(groupId);
    }

    @DeleteMapping("/delete/{groupId}/{memberId}")
    public void deleteMemberGroup(@PathVariable Long groupId, @PathVariable Long memberId){
        groupService.deleteMemberGroup(groupId, memberId);
    }
}
