package com.example.churchback2024.controller.response.member;

import com.example.churchback2024.dto.MemberDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class MemberLoginResponse {
    private String name;
    private String email;
    private Long memberId;
    private Boolean isNew;

    public MemberLoginResponse(MemberDto memberDto) {
        this.name = memberDto.getName();
        this.email = memberDto.getEmail();
        this.memberId = memberDto.getMemberId();
        this.isNew = memberDto.getIsNew();
    }
}
