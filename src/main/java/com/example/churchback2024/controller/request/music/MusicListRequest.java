package com.example.churchback2024.controller.request.music;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@Data
public class MusicListRequest {
    private Long memberId;
    private Long groupId;
    private List<Long> musicIdList;
}
