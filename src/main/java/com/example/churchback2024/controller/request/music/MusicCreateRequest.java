package com.example.churchback2024.controller.request.music;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Data
public class MusicCreateRequest {
    private String musicName;
    private String code;
    private String link;
    private String description;
    private Long groupId;
    private String version;
}
