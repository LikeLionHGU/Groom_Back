package com.example.churchback2024.dto;

import com.example.churchback2024.controller.request.member.MemberCreateRequest;
import com.example.churchback2024.controller.request.member.MemberUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class MemberDto {
    private Long memberId;
    private String nickname;
    private String position;
    private String email;

    public static MemberDto from(MemberCreateRequest request) {
        return MemberDto.builder()
                .email(request.getEmail())
                .nickname(request.getNickname())
                .position(request.getPosition())
                .build();
    }

    public static MemberDto from(MemberUpdateRequest request) {
        return MemberDto.builder()
                .email(request.getEmail())
                .nickname(request.getNickname())
                .position(request.getPosition())
                .build();
    }
}
