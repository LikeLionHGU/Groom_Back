package com.example.churchback2024.domain;

import com.example.churchback2024.dto.MusicDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Music extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long musicId;

    @Column(nullable = false)
    private String musicName;
    @Column(nullable = false, unique = true)
    private String code;
    @Column(nullable = false)
    private String link;
    private String description;
    private String musicImage;
    @Column(nullable = false)
    private Long folderId;

    // BaseEntity 에 regDate,modDate 는 상속

    public void update(MusicDto musicDto) {
        this.musicName = musicDto.getMusicName();
        this.code = musicDto.getCode();
        this.link = musicDto.getLink();
        this.description = musicDto.getDescription();
        this.musicImage = musicDto.getMusicImage();
        this.folderId = musicDto.getFolderId();
    }

    public static Music from(MusicDto musicDto) {
        return Music.builder()
                .musicName(musicDto.getMusicName())
                .code(musicDto.getCode())
                .link(musicDto.getLink())
                .description(musicDto.getDescription())
                .musicImage(musicDto.getMusicImage())
                .folderId(musicDto.getFolderId())
                .build();
    }
}
