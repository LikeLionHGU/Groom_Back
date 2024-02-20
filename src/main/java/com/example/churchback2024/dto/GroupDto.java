package com.example.churchback2024.dto;

import com.example.churchback2024.controller.request.group.GroupAddRequest;
import com.example.churchback2024.controller.request.group.GroupCreateRequest;
import com.example.churchback2024.controller.request.group.GroupUpdateRequest;
import com.example.churchback2024.domain.GroupC;
import com.example.churchback2024.domain.MemberGroup;
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
    private Long memberId;
    private String position;
    private String invitationCode;

    public static GroupDto from(GroupCreateRequest groupCreateRequest) {
        return GroupDto.builder()
                .groupName(groupCreateRequest.getGroupName())
                .memberId(groupCreateRequest.getMemberId())
                .position(groupCreateRequest.getPosition())
                .build();
    }
    public static GroupDto from(GroupAddRequest groupAddRequest) {
        return GroupDto.builder()
                .memberId(groupAddRequest.getMemberId())
                .position(groupAddRequest.getPosition())
                .invitationCode(groupAddRequest.getInvitationCode())
                .build();
    }
    public static GroupDto from(GroupUpdateRequest groupUpdateRequest) {
        return GroupDto.builder()
                .groupName(groupUpdateRequest.getGroupName())
                .build();
    }
    public static GroupDto from(GroupC groupC) {
        return GroupDto.builder()
                .groupId(groupC.getGroupId())
                .groupName(groupC.getGroupName())
                .build();
    }
    public static GroupDto from(MemberGroup memberGroup){
        return GroupDto.builder()
                .groupId(memberGroup.getGroupC().getGroupId())
                .groupName(memberGroup.getGroupC().getGroupName())
                .memberId(memberGroup.getMember().getMemberId())
                .position(memberGroup.getPosition())
                .build();
    }
}
