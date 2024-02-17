package com.example.churchback2024.domain;

import com.example.churchback2024.dto.S3Dto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Image extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;
    private String imageName;
    private String imagePath;

    public static Image from(S3Dto s3Dto) {
        return Image.builder()
                .imageName(s3Dto.getImageName())
                .imagePath(s3Dto.getImagePath())
                .build();
    }
}
