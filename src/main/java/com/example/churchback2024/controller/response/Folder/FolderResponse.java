package com.example.churchback2024.controller.response.Folder;


import com.example.churchback2024.domain.Folder;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FolderResponse {
    private String folderName;
    private String path;
    public FolderResponse(Folder folder) {
        this.folderName = folder.getFolderName();
        this.path = folder.getPath();
    }



}
