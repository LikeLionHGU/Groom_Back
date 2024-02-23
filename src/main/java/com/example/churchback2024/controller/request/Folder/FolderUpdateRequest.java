package com.example.churchback2024.controller.request.Folder;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Data
public class FolderUpdateRequest {
    private String folderName;
    private String path;
}
