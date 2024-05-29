package com.example.churchback2024.controller.response.music;

import com.example.churchback2024.domain.Music;
import com.example.churchback2024.dto.MusicDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MusicResponse {
    private String musicName;
    private String code;
    private String link;
    private String description;
    private String musicImageUrl;
    private Long musicId;
    private String version;
    public MusicResponse(Music music, String url) {
        this.musicName = music.getMusicName();
        this.code = music.getCode();
        this.link = music.getLink();
        this.description = music.getDescription();
        this.musicImageUrl = url;
        this.musicId = music.getMusicId();
        this.version = music.getVersion();
    }

    public MusicResponse (MusicDto musicDto) {
        this.musicName = musicDto.getMusicName();
        this.code = musicDto.getCode();
        this.link = musicDto.getLink();
        this.description = musicDto.getDescription();
        this.musicImageUrl = musicDto.getMusicImageUrl();
        this.musicId = musicDto.getMusicId();
        this.version = musicDto.getVersion();
    }
}
