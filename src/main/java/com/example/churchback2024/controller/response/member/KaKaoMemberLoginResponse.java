package com.example.churchback2024.controller.response.member;

import com.example.churchback2024.dto.MemberDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class KaKaoMemberLoginResponse {
    private String nickname;
    private String email;
    private Long memberId;
    private Boolean isNew;

    public KaKaoMemberLoginResponse(MemberDto dto) {
        this.nickname = dto.getName();
        this.email = dto.getEmail();
        this.memberId = dto.getMemberId();
        this.isNew = dto.getIsNew();
    }
}
