package com.example.churchback2024.dto;

import com.example.churchback2024.domain.MemberGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class MemberGroupDto {
    private Long memberId;
    private Long groupId;
    private String name;
    private String groupName;
    private String position;
    private String nickname;
    private String invitation_code;
    private String description;

    public static MemberGroupDto from(MemberGroup memberGroup) {
        return MemberGroupDto.builder()
                .memberId(memberGroup.getMember().getMemberId())
                .groupId(memberGroup.getGroupC().getGroupId())
                .name(memberGroup.getMember().getName())
                .groupName(memberGroup.getGroupC().getGroupName())
                .position(memberGroup.getPosition())
                .nickname(memberGroup.getNickname())
                .invitation_code(memberGroup.getGroupC().getInvitationCode())
                .description(memberGroup.getDescription())
                .build();
    }

}
