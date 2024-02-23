package com.example.churchback2024.controller;

import com.example.churchback2024.controller.request.Folder.FolderCreateRequest;
import com.example.churchback2024.controller.request.Folder.FolderUpdateRequest;
import com.example.churchback2024.controller.response.folder.FolderListResponse;
import com.example.churchback2024.controller.response.folder.FolderResponse;
import com.example.churchback2024.dto.FolderDto;
import com.example.churchback2024.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/church+/folder")

public class FolderController {
    private final FolderService folderService;

    @GetMapping("/list")
    public ResponseEntity<FolderListResponse> findFolderList(){
        FolderListResponse FolderListResponse = folderService.getFolderList();
        return ResponseEntity.ok(FolderListResponse);
    }

    @PostMapping("/create")
    public ResponseEntity<FolderResponse> createFolder(@RequestBody FolderCreateRequest request){
        FolderDto folderDto = folderService.createFolder(FolderDto.from(request));
        FolderResponse folderResponse = new FolderResponse(folderDto);
        return ResponseEntity.ok(folderResponse);
    }
    @PatchMapping("/{folderId}")
    public ResponseEntity<FolderResponse> update(@PathVariable Long folderId, @RequestBody FolderUpdateRequest request){
        FolderDto folderDto = folderService.updateFolder(folderId, FolderDto.from(request));
        FolderResponse folderResponse = new FolderResponse(folderDto);
        return ResponseEntity.ok(folderResponse);
    }
    @DeleteMapping("/{folderId}")
    public void delete(@PathVariable Long folderId){
        folderService.deleteFolder(folderId);
    }
    @GetMapping("/list/{path}")
    public ResponseEntity<FolderListResponse> getFolderByPath(@PathVariable String path) {
        FolderListResponse folderListResponse = folderService.getFolderByPath(path);
        return ResponseEntity.ok(folderListResponse);
    }
    @GetMapping("/list/{groupId}/{path}")
    public ResponseEntity<FolderListResponse> getFolderByPathAndGroupId(@PathVariable Long groupId, @PathVariable String path) {
        FolderListResponse folderListResponse = folderService.getFolderByPathAndGroupId(groupId, path);
        return ResponseEntity.ok(folderListResponse);
    }
}
