package com.example.churchback2024.controller.response.group;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupListResponse {
    private List<GroupResponse> groups;

    public GroupListResponse(List<GroupResponse> groups){
        this.groups = groups;
    }
}
