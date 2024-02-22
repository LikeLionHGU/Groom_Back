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
    private Long memberId;
    private Long  groupId;
    public FolderResponse(Folder folder) {
        this.folderId = folder.getFolderId();
        this.folderName = folder.getFolderName();
        this.path = extractPathBeforeDash(folder.getPath()); // 변경된 부분
        this.memberId = folder.getMemberGroup().getMember().getMemberId();
        this.groupId = folder.getMemberGroup().getGroupC().getGroupId();
    }

    public FolderResponse(FolderDto folderDto) {
        this.folderId = folderDto.getFolderId();
        this.folderName = folderDto.getFolderName();
        this.path = extractPathBeforeDash(folderDto.getPath()); // 변경된 부분
        this.memberId = folderDto.getMemberId();
        this.groupId = folderDto.getGroupId();
    }
    private String extractPathBeforeDash(String path) {
        if (path != null && path.contains("-")) {
            return path.split("-")[0];
        }
        return path; // 구분자가 없는 경우 전체 경로 반환
    }
}
