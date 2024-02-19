package com.example.churchback2024.service;

import com.example.churchback2024.controller.response.folder.FolderListResponse;
import com.example.churchback2024.controller.response.folder.FolderResponse;
import com.example.churchback2024.domain.Folder;
import com.example.churchback2024.dto.FolderDto;
import com.example.churchback2024.exception.folder.DuplicateFolderException;
import com.example.churchback2024.exception.folder.FolderNotFoundException;
import com.example.churchback2024.repository.FolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FolderService {
    private final FolderRepository folderRepository;

    public void createFolder(FolderDto folderDto) {
        Folder folder = folderRepository.findByFolderName(folderDto.getFolderName());
        if(folder != null){
            throw new DuplicateFolderException();
        }
        folderRepository.save(Folder.from(folderDto));
    }

    public FolderListResponse getFolderList() {
        List<Folder> folders = folderRepository.findAll();
        List<FolderResponse> folderResponses = folders.stream()
                .map(FolderResponse::new)
                .collect(Collectors.toList());
        return new FolderListResponse(folderResponses);
    }

    public FolderResponse getFolderByPath(String path) {
        System.out.println("path = " + path);
        Folder folder = folderRepository.findByPath(path);
        if (folder == null) {
            throw new FolderNotFoundException();
        }
        return new FolderResponse(folder);
    }

    public Folder updateFolder(Long folderId, FolderDto folderDto) {
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(FolderNotFoundException::new);
        folder.update(folderDto);
        folderRepository.save(folder);
        return folder;
    }

    public void deleteFolder(Long folderId) {
        folderRepository.deleteById(folderId);
    }


}

// 완성