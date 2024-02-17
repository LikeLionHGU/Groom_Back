package com.example.churchback2024.dto;

import com.example.churchback2024.controller.request.member.MemberCreateRequest;
import com.example.churchback2024.controller.request.member.MemberUpdateRequest;
import com.example.churchback2024.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class MemberDto {
    private String name;
    private String email;
    private Long memberId;
    private String position;
    private String g_id;

    public static MemberDto from(MemberCreateRequest request) {
        return MemberDto.builder()
                .position(request.getPosition())
                .build();
    }
    public static MemberDto from(MemberUpdateRequest request) {
        return MemberDto.builder()
                .position(request.getPosition())
                .build();
    }
    public static MemberDto from(String id) {
        return MemberDto.builder()
                .g_id(id)
                .build();
    }
    public static MemberDto from(Member member) {
        return MemberDto.builder()
                .name(member.getName())
                .email(member.getEmail())
                .position(member.getPosition())
                .g_id(member.getGoogleId())
                .build();
    }
}
