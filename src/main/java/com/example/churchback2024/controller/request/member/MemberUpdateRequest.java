package com.example.churchback2024.controller.request.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberUpdateRequest {
    private String email;
    private String nickname;
    private String position;
}