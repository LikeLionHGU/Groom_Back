package com.example.churchback2024.dto;

import com.example.churchback2024.controller.request.Folder.FolderCreateRequest;
import com.example.churchback2024.controller.request.Folder.FolderUpdateRequest;
import com.example.churchback2024.domain.MemberGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter

public class FolderDto {
    private Long folderId;
    private String folderName;
    private String path;
    private Long memberId;
    private Long groupId;

    public static FolderDto from(FolderCreateRequest request) {
        return FolderDto.builder()
                .folderName(request.getFolderName())
                .path(request.getPath())
                .memberId(request.getMemberId())
                .groupId(request.getGroupId())
                .build();
    }

    public static FolderDto from(FolderUpdateRequest request) {
        return FolderDto.builder()
                .folderName(request.getFolderName())
                .path(request.getPath())
                .build();
    }


}
