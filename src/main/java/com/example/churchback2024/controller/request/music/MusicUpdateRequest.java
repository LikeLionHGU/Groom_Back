package com.example.churchback2024.controller.request.music;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Data
public class MusicUpdateRequest {
//    private Long musicId;
    private String musicName;
    private String code;
    private String link;
    private String description;
    private String musicImage;
    private String path;
  }


