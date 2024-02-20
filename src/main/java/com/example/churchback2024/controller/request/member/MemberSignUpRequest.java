package com.example.churchback2024.controller.request.member;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
public class MemberSignUpRequest {
    private String email;
    private String name;
}
