package com.example.churchback2024.service;

import com.example.churchback2024.controller.response.group.GroupListResponse;
import com.example.churchback2024.controller.response.group.GroupResponse;
import com.example.churchback2024.domain.GroupC;
import com.example.churchback2024.dto.GroupDto;
import com.example.churchback2024.exception.group.DuplicateGroupException;
import com.example.churchback2024.exception.group.GroupNotFoundException;
import com.example.churchback2024.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

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
        if (groupC != null) {
            throw new DuplicateGroupException();
        }
        String invitationCode = generateRandomInvitationCode();
        GroupC groupC1 = groupRepository.findByInvitationCode(invitationCode);
        if (groupC1 != null) {
            throw new RuntimeException("이미 존재하는 코드입니다.");
        }
        groupRepository.save(GroupC.from(groupDto, invitationCode));
    }

    public String generateRandomInvitationCode() {
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int length = 6;

        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
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
