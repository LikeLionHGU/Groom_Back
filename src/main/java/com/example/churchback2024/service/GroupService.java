package com.example.churchback2024.service;

import com.example.churchback2024.controller.response.group.GroupListResponse;
import com.example.churchback2024.controller.response.group.GroupResponse;
import com.example.churchback2024.domain.GroupC;
import com.example.churchback2024.dto.GroupDto;
import com.example.churchback2024.exception.DuplicateGroupException;
import com.example.churchback2024.exception.GroupNotFoundException;
import com.example.churchback2024.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupService {
    private final GroupRepository groupRepository;
    public GroupListResponse getGroupList() {
        List<GroupC> groupCS = groupRepository.findAll();
        List<GroupResponse> groupResponses = groupCS.stream()
                .map(GroupResponse::new)
                .collect(java.util.stream.Collectors.toList());
        return new GroupListResponse(groupResponses);
    }

    public void createGroup(GroupDto groupDto) {
        GroupC groupC = groupRepository.findByGroupName(groupDto.getGroupName());
        if(groupC != null){
            throw new DuplicateGroupException();
        }
        groupRepository.save(GroupC.from(groupDto));
    }

    public GroupC updateGroup(Long groupId, GroupDto from) {
        GroupC groupC = groupRepository.findById(groupId).orElseThrow(() -> new GroupNotFoundException());
        groupC.update(from);
        groupRepository.save(groupC);
        return groupC;
    }

    public void deleteGroup(Long groupId) {
        groupRepository.deleteById(groupId);
    }
}
