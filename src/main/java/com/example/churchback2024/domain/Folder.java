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
    @JoinColumn(name = "memberGroupId")
    private MemberGroup memberGroup;

    public void update(FolderDto FolderDto) {
        this.folderName = FolderDto.getFolderName();
//        this.path = FolderDto.getPath();
    }

    public static Folder from(FolderDto folderDto, MemberGroup memberGroup) {
        return Folder.builder()
                .folderName(folderDto.getFolderName())
                .path(folderDto.getPath()+"-"+folderDto.getFolderName())
                .memberGroup(memberGroup)
                .build();
    }
}