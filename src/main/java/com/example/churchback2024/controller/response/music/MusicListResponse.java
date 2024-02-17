package com.example.churchback2024.controller.response.music;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class MusicListResponse {
    private List<MusicResponse> musics;

    public MusicListResponse(List<MusicResponse> musics) {
        this.musics = musics;
    }
}
