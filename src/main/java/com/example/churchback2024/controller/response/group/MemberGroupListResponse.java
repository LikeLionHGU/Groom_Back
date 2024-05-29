package com.example.churchback2024.controller.response.group;

import com.example.churchback2024.dto.GroupDto;
import com.example.churchback2024.dto.MemberGroupDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberGroupListResponse {
    private List<MemberGroupResponse> memberGroups;

    public MemberGroupListResponse(List<MemberGroupDto> dtoList) {
        this.memberGroups = dtoList.stream()
                .map(MemberGroupResponse::new)
                .toList();
    }
}
