package com.example.churchback2024.controller.request.music;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MusicUpdateRequest {
//    private Long musicId;
    private String musicName;
    private String code;
    private String link;
    private String description;
    private String musicImage;
  }


