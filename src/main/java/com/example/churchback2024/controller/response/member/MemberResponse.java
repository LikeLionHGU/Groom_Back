package com.example.churchback2024.controller.response.member;

import com.example.churchback2024.domain.Member;
import com.example.churchback2024.dto.MemberDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberResponse {
    private String name;
    private String email;
    private Long memberId;
    private Boolean isNew;
    public MemberResponse(MemberDto memberDto) {
        this.name = memberDto.getName();
        this.email = memberDto.getEmail();
        this.memberId = memberDto.getMemberId();
        this.isNew = memberDto.getIsNew();
    }
}
