package com.example.churchback2024.controller;

import com.example.churchback2024.controller.request.Folder.FolderCreateRequest;
import com.example.churchback2024.controller.request.Folder.FolderUpdateRequest;
import com.example.churchback2024.controller.response.folder.FolderListResponse;
import com.example.churchback2024.controller.response.folder.FolderResponse;
import com.example.churchback2024.domain.Folder;
import com.example.churchback2024.dto.FolderDto;
import com.example.churchback2024.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/church+/folder")

public class FolderController {

    private final FolderService folderService;

    // 전체 폴더 리스트 조회
    @GetMapping("/list")
    public ResponseEntity<FolderListResponse> findFolderList(){
        FolderListResponse FolderListResponse = folderService.getFolderList();
        return ResponseEntity.ok(FolderListResponse);
    }

    // 새 폴더 생성
    @PostMapping("/create")
    public void createFolder(@RequestBody FolderCreateRequest request){
        folderService.createFolder(FolderDto.from(request));
    }

    // 폴더 수정
    @PatchMapping("/{folderId}")
    public ResponseEntity<Folder> update(@PathVariable Long folderId, @RequestBody FolderUpdateRequest request){
        return ResponseEntity.ok(folderService.updateFolder(folderId, FolderDto.from(request)));
    }

    // 폴더 삭제
    @DeleteMapping("/{folderId}")
    public void delete(@PathVariable Long folderId){
        folderService.deleteFolder(folderId);
    }

    // 경로를 이용해서 폴더 조회
    @GetMapping("list/{path}")
    public ResponseEntity<FolderResponse> getFolderByPath(@PathVariable("path") String path) {
        return ResponseEntity.ok(folderService.getFolderByPath(path));
    }

    // 학생의 메일, 그룹명, 경로를 이용해서 폴더 조회 (로그인 한 사람이 가입한 그룹의 폴더만 조회 가능)
    @GetMapping("list/{email}/{groupName}/{path}")
    public ResponseEntity<FolderResponse> getFolderByMemberAndGroupAndPath(@PathVariable String email, @PathVariable String groupName, @PathVariable("path") String path) {
        return ResponseEntity.ok(folderService.getFolderByMemberAndGroupAndPath(email, groupName, path));
    }

}
