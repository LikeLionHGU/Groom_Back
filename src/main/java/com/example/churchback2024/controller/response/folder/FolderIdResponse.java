package com.example.churchback2024.controller.response.folder;

import com.example.churchback2024.dto.FolderDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FolderIdResponse {
    private Long folderId;

    public FolderIdResponse(FolderDto folderDto) {
        this.folderId = folderDto.getFolderId();
    }
}
