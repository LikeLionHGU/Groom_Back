package com.example.churchback2024.controller;

import com.example.churchback2024.controller.request.Folder.FolderCreateRequest;
import com.example.churchback2024.controller.request.Folder.FolderUpdateRequest;
import com.example.churchback2024.controller.response.Folder.FolderListResponse;
import com.example.churchback2024.domain.Folder;
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
    public void createFolder(@RequestBody FolderCreateRequest request){
        folderService.createFolder(FolderDto.from(request));
    }

    @PatchMapping("/{folderId}")
    public ResponseEntity<Folder> update(@PathVariable Long folderId, @RequestBody FolderUpdateRequest request){
        return ResponseEntity.ok(folderService.updateFolder(folderId, FolderDto.from(request)));
    }

    @DeleteMapping("/{folderId}")
    public void delete(@PathVariable Long folderId){
        folderService.deleteFolder(folderId);
    }


}
