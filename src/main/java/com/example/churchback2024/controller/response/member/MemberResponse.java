package com.example.churchback2024.controller.response.member;

import com.example.churchback2024.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberResponse {
    private String nickname;
    private String position;
    public MemberResponse(Member member) {
        this.nickname = member.getNickname();
        this.position = member.getPosition();
    }
}
