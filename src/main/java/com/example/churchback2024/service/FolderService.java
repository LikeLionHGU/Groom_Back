package com.example.churchback2024.service;

import com.example.churchback2024.controller.response.folder.FolderListResponse;
import com.example.churchback2024.controller.response.folder.FolderResponse;
import com.example.churchback2024.domain.Folder;
import com.example.churchback2024.domain.GroupC;
import com.example.churchback2024.domain.Member;
import com.example.churchback2024.domain.MemberGroup;
import com.example.churchback2024.dto.FolderDto;
import com.example.churchback2024.exception.folder.DuplicateFolderException;
import com.example.churchback2024.exception.folder.FolderNotFoundException;
import com.example.churchback2024.exception.group.GroupNotFoundException;
import com.example.churchback2024.exception.groupMember.MemberGroupNotFoundException;
import com.example.churchback2024.exception.member.MemberNotFoundException;
import com.example.churchback2024.repository.FolderRepository;
import com.example.churchback2024.repository.GroupRepository;
import com.example.churchback2024.repository.MemberGroupRepository;
import com.example.churchback2024.repository.MemberRepository;
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
    private final MemberRepository memberRepository;
    private final GroupRepository groupRepository;

    public void createFolder(FolderDto folderDto) {
        MemberGroup memberGroup = memberGroupRepository.findByMember_MemberIdAndGroupC_GroupId(folderDto.getMemberId(), folderDto.getGroupId());
        Folder folder = folderRepository.findByFolderNameAndMemberGroup(folderDto.getFolderName(), memberGroup);

        if(folder != null){
            throw new DuplicateFolderException();
        }
        if(memberGroup == null){
            throw new MemberGroupNotFoundException();
        }
        folderRepository.save(Folder.from(folderDto, memberGroup));
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


    public FolderResponse getFolderByMemberAndGroupAndPath(String email, String groupName, String path) {
        Member member = memberRepository.findByEmail(email);
        if (member == null) {
            throw new MemberNotFoundException();
        }
        GroupC group = groupRepository.findByGroupName(groupName);
        if (group == null) {
            throw new GroupNotFoundException();
        }
        MemberGroup memberGroup = memberGroupRepository.findByMember_MemberIdAndGroupC_GroupId(member.getMemberId(), group.getGroupId());
        if (memberGroup == null) {
            throw new MemberGroupNotFoundException();
        }
        Folder folder = folderRepository.findByPathAndMemberGroup(path, memberGroup);
        if (folder == null) {
            throw new FolderNotFoundException();
        }
        return new FolderResponse(folder);
    }
}

// 완성