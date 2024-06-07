package com.example.churchback2024.controller.request.group;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Data
public class GroupMemberUpdateRequest {
    private Long memberId;
    private Long groupId;
    private String description;
}
