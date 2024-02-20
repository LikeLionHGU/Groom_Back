package com.example.churchback2024.controller.request.group;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Data
public class GroupAddRequest {
    private Long memberId;
    private String invitationCode;
    private String position;
}
