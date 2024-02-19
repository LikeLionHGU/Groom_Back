package com.example.churchback2024.controller.request.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberAddRequest {
    private String email;
//    private String name;
    private String position;
    private Long groupId;
}