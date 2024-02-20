package com.example.churchback2024.controller.response.member;

import com.example.churchback2024.dto.MemberDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class MemberListResponse {
    private List<MemberResponse> members;
    public MemberListResponse(List<MemberDto> dtoList) {
        this.members = dtoList.stream()
                .map(MemberResponse::new)
                .toList();
    }
}

