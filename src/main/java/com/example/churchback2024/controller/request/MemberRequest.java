package com.example.churchback2024.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberRequest {
    private Long memberId;
    private String nickname;
    private String position;
}
