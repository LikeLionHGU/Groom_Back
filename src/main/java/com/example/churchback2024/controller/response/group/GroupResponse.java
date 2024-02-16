package com.example.churchback2024.controller.response.group;

import com.example.churchback2024.domain.GroupC;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupResponse {
    private String groupName;

    public GroupResponse(GroupC groupC) {
        this.groupName = groupC.getGroupName();
    }
}
