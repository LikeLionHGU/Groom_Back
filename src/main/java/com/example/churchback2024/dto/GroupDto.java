package com.example.churchback2024.dto;

import com.example.churchback2024.controller.request.group.GroupCreateRequest;
import com.example.churchback2024.domain.GroupC;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class GroupDto {
    private Long groupId;
    private String groupName;


    public static GroupDto from(GroupCreateRequest groupCreateRequest) {
        return GroupDto.builder()
                .groupName(groupCreateRequest.getGroupName())
                .build();
    }

    public static GroupDto from(GroupC groupC) {
        return GroupDto.builder()
                .groupId(groupC.getGroupId())
                .groupName(groupC.getGroupName())
                .build();
    }
}
