package com.example.churchback2024.controller.response.member;

import com.example.churchback2024.dto.GroupDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class MemberInfoResponse {
    private String nickname;
    private String position;

    public MemberInfoResponse(GroupDto groupDto) {
        this.nickname = groupDto.getNickname();
        this.position = groupDto.getPosition();
    }
}
