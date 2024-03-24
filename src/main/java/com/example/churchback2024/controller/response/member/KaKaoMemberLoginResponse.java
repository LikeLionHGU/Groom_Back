package com.example.churchback2024.controller.response.member;

import com.example.churchback2024.dto.MemberDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class KaKaoMemberLoginResponse {
    private String nickname;

    public KaKaoMemberLoginResponse(MemberDto dto) {
        this.nickname = dto.getName();
    }
}
