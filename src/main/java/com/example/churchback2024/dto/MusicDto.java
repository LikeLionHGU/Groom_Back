package com.example.churchback2024.dto;

import com.example.churchback2024.controller.request.music.MusicCreateRequest;
import com.example.churchback2024.controller.request.music.MusicUpdateRequest;
import com.example.churchback2024.domain.Music;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class MusicDto {

    private Long musicId;
    private String musicName;
    private String code;
    private String link;
    private String description;
    private String musicImageUrl;
    private Long groupId;
    private String version;

    public static MusicDto from(MusicCreateRequest request) {
        return MusicDto.builder()
                .musicName(request.getMusicName())
                .code(request.getCode())
                .link(request.getLink())
                .description(request.getDescription())
                .groupId(request.getGroupId())
                .version(request.getVersion())
                .build();
    }

    public static MusicDto from(Music music){
        return MusicDto.builder()
                .musicId(music.getMusicId())
                .musicName(music.getMusicName())
                .code(music.getCode())
                .link(music.getLink())
                .description(music.getDescription())
                .musicImageUrl(music.getMusicImageUrl())
                .version(music.getVersion())
                .build();
    }
    public static MusicDto from(Music music, String url){
        return MusicDto.builder()
                .musicId(music.getMusicId())
                .musicName(music.getMusicName())
                .code(music.getCode())
                .link(music.getLink())
                .description(music.getDescription())
                .musicImageUrl(url)
                .version(music.getVersion())
                .build();
    }
    public static MusicDto from(MusicUpdateRequest request) {
        return MusicDto.builder()
                .musicName(request.getMusicName())
                .code(request.getCode())
                .link(request.getLink())
                .description(request.getDescription())
                .version(request.getVersion())
                .build();
    }
}
