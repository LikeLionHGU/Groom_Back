package com.example.churchback2024.controller.response.group;

import com.example.churchback2024.domain.MemberGroup;
import com.example.churchback2024.dto.MemberGroupDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberGroupResponse {
    private Long groupId;
    private String groupName;
    private Long memberId;
    private String nickname;
    private String position;
    private String invitation_code;

    public MemberGroupResponse(MemberGroup memberGroup){
        this.groupId = memberGroup.getGroupC().getGroupId();
        this.groupName = memberGroup.getGroupC().getGroupName();
        this.memberId = memberGroup.getMember().getMemberId();
        this.position = memberGroup.getPosition();
        this.nickname = memberGroup.getNickname();
    }

    public MemberGroupResponse(MemberGroupDto memberGroupDto) {
        this.groupId = memberGroupDto.getGroupId();
        this.groupName = memberGroupDto.getGroupName();
        this.memberId = memberGroupDto.getMemberId();
        this.position = memberGroupDto.getPosition();
        this.nickname = memberGroupDto.getNickname();
        this.invitation_code = memberGroupDto.getInvitation_code();
    }
}
