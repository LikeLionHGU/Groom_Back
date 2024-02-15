package com.example.churchback2024.dto;

import com.example.churchback2024.controller.request.MemberRequest;
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

    public static MemberDto from(MemberRequest request) {
        return MemberDto.builder()
                .memberId(request.getMemberId())
                .nickname(request.getNickname())
                .position(request.getPosition())
                .build();
    }
}
