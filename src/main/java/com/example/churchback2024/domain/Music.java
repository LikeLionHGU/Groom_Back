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
    private String musicName;
    private String code;
    private String link;
    private String description;
    private String musicImageUrl;
    private String version;

    @ManyToOne
    @JoinColumn(name = "groupId")
    private GroupC group;

    public void update(MusicDto musicDto, String url) {
        this.musicName = musicDto.getMusicName();
        this.code = musicDto.getCode();
        this.link = musicDto.getLink();
        this.description = musicDto.getDescription();
        this.musicImageUrl = url;
        this.version = musicDto.getVersion();
    }

    public static Music from(MusicDto musicDto) {
        return Music.builder()
                .musicName(musicDto.getMusicName())
                .code(musicDto.getCode())
                .link(musicDto.getLink())
                .description(musicDto.getDescription())
                .musicImageUrl(musicDto.getMusicImageUrl())
                .version(musicDto.getVersion())
                .build();
    }
    public static Music from(MusicDto musicDto, GroupC group, String musicImageUrl) {
        return Music.builder()
                .musicName(musicDto.getMusicName())
                .code(musicDto.getCode())
                .link(musicDto.getLink())
                .description(musicDto.getDescription())
                .musicImageUrl(musicImageUrl)
                .group(group)
                .version(musicDto.getVersion())
                .build();
    }
}
