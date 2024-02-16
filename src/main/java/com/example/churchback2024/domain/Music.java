package com.example.churchback2024.domain;

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
    private Long SheetMusicId;

    @Column(nullable = false)
    private String music_name;
    @Column(nullable = false)
    private String code;
    @Column(nullable = false, unique = true)
    private String link;
    private String description;
    private String music_image;


    }
