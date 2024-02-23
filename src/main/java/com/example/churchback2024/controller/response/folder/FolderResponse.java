package com.example.churchback2024.controller.response.folder;


import com.example.churchback2024.domain.Folder;


import com.example.churchback2024.dto.FolderDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FolderResponse {
    private Long folderId;
    private String folderName;
    private String path;
    private Long  groupId;
    public FolderResponse(Folder folder) {
        this.folderId = folder.getFolderId();
        this.folderName = folder.getFolderName();
        this.path = folder.getPath();
        this.groupId = folder.getGroup().getGroupId();
    }

    public FolderResponse(FolderDto folderDto) {
        this.folderId = folderDto.getFolderId();
        this.folderName = folderDto.getFolderName();
        this.path = folderDto.getPath();
        this.groupId = folderDto.getGroupId();
    }
}
