package com.example.churchback2024.service;

import com.example.churchback2024.controller.response.group.GroupListResponse;
import com.example.churchback2024.controller.response.group.GroupResponse;
import com.example.churchback2024.domain.GroupC;
import com.example.churchback2024.domain.Member;
import com.example.churchback2024.domain.MemberGroup;
import com.example.churchback2024.dto.GroupDto;
import com.example.churchback2024.exception.group.DuplicateGroupException;
import com.example.churchback2024.exception.group.GroupNotFoundException;
import com.example.churchback2024.exception.member.MemberNotFoundException;
import com.example.churchback2024.repository.GroupRepository;
import com.example.churchback2024.repository.MemberGroupRepository;
import com.example.churchback2024.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupService {
    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;
    private final MemberGroupRepository memberGroupRepository;
    public GroupListResponse getGroupList() {
        List<GroupC> groupCS = groupRepository.findAll();
        List<GroupResponse> groupResponses = groupCS.stream()
                .map(GroupResponse::new)
                .collect(java.util.stream.Collectors.toList());
        return new GroupListResponse(groupResponses);
    }

    public GroupDto createGroup(GroupDto groupDto) {
        GroupC existingGroup = groupRepository.findByGroupName(groupDto.getGroupName());
        if (existingGroup != null) {
            throw new DuplicateGroupException();
        }
        Member member = memberRepository.findByMemberId(groupDto.getMemberId());
        if (member == null) {
            throw new MemberNotFoundException();
        }

        GroupC newGroup = GroupC.from(groupDto);
        groupRepository.save(newGroup);

        MemberGroup memberGroup = MemberGroup.from(member, newGroup, groupDto.getPosition());
        memberGroupRepository.save(memberGroup);
        return GroupDto.from(newGroup);
    }

    public GroupResponse updateGroup(Long groupId, GroupDto groupDto) {
        GroupC groupC = groupRepository.findById(groupId).orElseThrow(() -> new GroupNotFoundException());
        GroupC existingGroup = groupRepository.findByGroupName(groupDto.getGroupName());

        if (existingGroup != null && !existingGroup.getGroupId().equals(groupId)) {
            throw new DuplicateGroupException();
        }

        groupC.update(groupDto);
        groupRepository.save(groupC);
        return new GroupResponse(groupC);
    }

    public void deleteGroup(Long groupId) {
        groupRepository.deleteById(groupId);
    }
}
