package com.example.churchback2024.service;

import com.example.churchback2024.controller.response.folder.FolderListResponse;
import com.example.churchback2024.controller.response.folder.FolderResponse;
import com.example.churchback2024.domain.Folder;
import com.example.churchback2024.domain.MemberGroup;
import com.example.churchback2024.dto.FolderDto;
import com.example.churchback2024.exception.folder.DuplicateFolderException;
import com.example.churchback2024.exception.folder.FolderNotFoundException;
import com.example.churchback2024.exception.groupMember.MemberGroupNotFoundException;
import com.example.churchback2024.repository.FolderRepository;
import com.example.churchback2024.repository.MemberGroupRepository;
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
    private final MemberGroupRepository memberGroupRepository;

    public FolderDto createFolder(FolderDto folderDto) {
        MemberGroup memberGroup = memberGroupRepository.findByMember_MemberIdAndGroupC_GroupId(folderDto.getMemberId(), folderDto.getGroupId());
        if (memberGroup != null) {
            Folder existingFolder = folderRepository.findByFolderNameAndPathAndMemberGroup_GroupC_GroupId(
                    folderDto.getFolderName(), folderDto.getPath(), folderDto.getGroupId());

            if (existingFolder != null) {
                throw new DuplicateFolderException();
            }

            Folder newFolder = Folder.from(folderDto, memberGroup);
            folderRepository.save(newFolder);
            return FolderDto.from(newFolder);
        } else {
            throw new MemberGroupNotFoundException();
        }
    }




    public FolderListResponse getFolderList() {
        List<Folder> folders = folderRepository.findAll();
        List<FolderResponse> folderResponses = folders.stream()
                .map(FolderResponse::new)
                .collect(Collectors.toList());
        return new FolderListResponse(folderResponses);
    }

    public FolderListResponse getFolderByPath(String path) {
        String firstPartOfPath = path.split("-")[0];
        List<Folder> folders = folderRepository.findByPath(firstPartOfPath);
        if (folders.isEmpty()) {
            throw new FolderNotFoundException();
        }

        List<FolderResponse> folderResponses = folders.stream()
                .map(FolderResponse::new)
                .collect(Collectors.toList());

        return new FolderListResponse(folderResponses);
    }
    public FolderListResponse getFolderByPathAndGroupId(String path, Long groupId) {
        String firstPartOfPath = path.split("-")[0];
        List<Folder> folders = folderRepository.findAllByPathAndMemberGroup_GroupC_GroupId(firstPartOfPath, groupId);
        if (folders.isEmpty()) {
            throw new FolderNotFoundException();
        }

        List<FolderResponse> folderResponses = folders.stream()
                .map(FolderResponse::new)
                .collect(Collectors.toList());

        return new FolderListResponse(folderResponses);
    }
    public FolderDto updateFolder(Long folderId, FolderDto folderDto) {
        Folder folder = folderRepository.findByFolderId(folderId);
        if (folder == null) {
            throw new FolderNotFoundException();
        }
        folder.update(folderDto);
        folderRepository.save(folder);
        return FolderDto.from(folder);
    }
    public void deleteFolder(Long folderId) {
        folderRepository.deleteById(folderId);
    }
    private String extractPathBeforeDash(String path) {
        System.out.println("path: " + path);
        if (path != null && path.contains("-")) {
            return path.split("-")[0];
        }
        return path; // 구분자가 없는 경우 전체 경로 반환
    }
}

// 완성