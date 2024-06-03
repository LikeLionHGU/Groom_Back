package com.example.churchback2024.controller.response.setlist;

import com.example.churchback2024.domain.MusicSetList;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MusicSetListResponse {
    private Long musicId;
    private String description;

    public MusicSetListResponse(MusicSetList musicSetList) {
        this.musicId = musicSetList.getMusic().getMusicId();
        this.description = musicSetList.getDescription();
    }
}
