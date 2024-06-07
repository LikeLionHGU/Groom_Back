package com.example.churchback2024.dto;

import com.example.churchback2024.controller.request.member.KaKaoMemberAddRequest;
import com.example.churchback2024.controller.request.member.KaKaoMemberSignUpRequest;
import com.example.churchback2024.controller.request.member.MemberAddRequest;
import com.example.churchback2024.controller.request.member.MemberSignUpRequest;
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
    private Long groupId;
    private Boolean isNew;

    public static MemberDto from(MemberSignUpRequest request){
        return MemberDto.builder()
                .name(request.getName())
                .email(request.getEmail())
                .build();
    }
    public static MemberDto from(MemberAddRequest request) {
        return MemberDto.builder()
                .email(request.getEmail())
                .build();
    }
    public static MemberDto from(String name, String email, Long memberId) {
        return MemberDto.builder()
                .name(name)
                .email(email)
                .memberId(memberId)
                .build();
    }
    public static MemberDto from(Member member) {
        return MemberDto.builder()
                .name(member.getName())
                .email(member.getEmail())
                .isNew(member.getIsNew())
                .memberId(member.getMemberId())
                .build();
    }

    public static MemberDto from(KaKaoMemberSignUpRequest request) {
        return MemberDto.builder()
                .name(request.getNickname())
                .email(request.getEmail())
                .build();
    }
    public static MemberDto from(KaKaoMemberAddRequest request) {
        return MemberDto.builder()
                .name(request.getNickname())
                .email(request.getEmail())
                .build();
    }
    public static MemberDto from(String name, String email) {
        return MemberDto.builder()
                .name(name)
                .email(email)
                .build();
    }
}
