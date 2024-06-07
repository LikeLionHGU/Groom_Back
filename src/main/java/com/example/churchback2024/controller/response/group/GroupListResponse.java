package com.example.churchback2024.controller.response.group;

import com.example.churchback2024.dto.GroupDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupListResponse {
    private List<GroupResponse> groups;

    public GroupListResponse(List<GroupDto> dtoList) {
        this.groups = dtoList.stream()
                .map(GroupResponse::new)
                .toList();
    }
}
