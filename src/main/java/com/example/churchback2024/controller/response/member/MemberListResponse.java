package com.example.churchback2024.controller.response.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class MemberListResponse{
    private List<MemberResponse> members;

    public MemberListResponse(List<MemberResponse> members){
        this.members = members;
    }
}

