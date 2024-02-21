package com.example.churchback2024.controller.request.group;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Data
public class GroupCreateRequest {
    private Long memberId;
    private String groupName;
    private String position;
    private String nickname;
}
