package com.example.churchback2024.controller.response.group;

import com.example.churchback2024.domain.GroupC;
import com.example.churchback2024.domain.MemberGroup;
import com.example.churchback2024.dto.GroupDto;
import com.example.churchback2024.dto.MemberDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupResponse {
    private Long groupId;
    private String groupName;

    public GroupResponse(GroupC groupC) {
        this.groupId = groupC.getGroupId();
        this.groupName = groupC.getGroupName();
    }

    public GroupResponse(GroupDto groupDto) {
        this.groupId = groupDto.getGroupId();
        this.groupName = groupDto.getGroupName();
    }

    public GroupResponse(MemberGroup memberGroup) {
        this.groupId = memberGroup.getGroupC().getGroupId();
        this.groupName = memberGroup.getGroupC().getGroupName();
    }
}