package com.example.churchback2024.controller.response.member;

import com.example.churchback2024.domain.Member;

public class MemberResponse {
    private String nickname;
    private String position;
    public MemberResponse(Member member) {
        this.nickname = member.getNickname();
        this.position = member.getPosition();
    }
}
