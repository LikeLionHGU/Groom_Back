package com.example.churchback2024.domain;

import com.example.churchback2024.dto.FolderDto;
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
public class Folder extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long folderId;
    @Column(nullable = false)
    private String folderName;

    @Column(nullable = false)
    private String path;

    @ManyToOne
    @JoinColumn(name = "groupId")
    private GroupC group;


    public void update(FolderDto folderDto) {
        this.folderName = folderDto.getFolderName();
        this.path = folderDto.getPath() + "-" + folderDto.getFolderName();
    }
    public static Folder from(FolderDto folderDto, GroupC group) {
        return Folder.builder()
                .folderName(folderDto.getFolderName())
                .path(folderDto.getPath() + "-" + folderDto.getFolderName())
                .group(group)
                .build();
    }
}