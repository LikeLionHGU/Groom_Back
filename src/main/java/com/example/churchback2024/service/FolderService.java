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
            Folder existingFolder = folderRepository.findByFolderNameAndMemberGroup(folderDto.getFolderName(), memberGroup);
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
        List<Folder> folders = folderRepository.findByPath(path);
        if (folders.isEmpty()) {
            throw new FolderNotFoundException();
        }

        List<FolderResponse> folderResponses = folders.stream()
                .map(FolderResponse::new)
                .collect(Collectors.toList());

        return new FolderListResponse(folderResponses);
    }
    public FolderListResponse getFolderByPathAndGroupId(String path, Long groupId) {
        List<Folder> folders = folderRepository.findByPathAndMemberGroup_GroupC_GroupId(path, groupId);
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
}

// 완성