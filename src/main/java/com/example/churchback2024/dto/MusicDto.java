package com.example.churchback2024.dto;

import com.example.churchback2024.controller.request.music.MusicCreateRequest;
import com.example.churchback2024.controller.request.music.MusicUpdateRequest;
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
    private String musicImage;


    public static MusicDto from(MusicCreateRequest request) {
        return MusicDto.builder()
                .musicName(request.getMusicName())
                .code(request.getCode())
                .link(request.getLinkcode())
                .description(request.getDescription())
                .musicImage(request.getMusicImage())
                .build();
    }

    public static MusicDto from(MusicUpdateRequest request) {
        return MusicDto.builder()
                .musicName(request.getMusicName())
                .code(request.getCode())
                .link(request.getLink())
                .description(request.getDescription())
                .musicImage(request.getMusicImage())
                .build();
    }
}
