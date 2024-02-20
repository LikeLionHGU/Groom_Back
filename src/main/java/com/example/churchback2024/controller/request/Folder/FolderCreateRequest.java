package com.example.churchback2024.controller.request.Folder;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Data
public class FolderCreateRequest {
    private String folderName;
    private String path;
    private Long memberId;
    private Long groupId;
}
