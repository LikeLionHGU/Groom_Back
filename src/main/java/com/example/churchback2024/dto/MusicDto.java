package com.example.churchback2024.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter

public class MusicDto {
    private Long memberId;
    private String nickname;
    private String position;
    private String email;
}
