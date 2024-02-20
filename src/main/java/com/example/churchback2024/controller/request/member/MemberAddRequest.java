package com.example.churchback2024.controller.request.member;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Data
public class MemberAddRequest {
    private String email;
    private String name;
}