package com.example.churchback2024.repository;

import com.example.churchback2024.domain.Folder;
import com.example.churchback2024.domain.MemberGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Long> {
    Folder findByFolderId(Long folderId);
    List<Folder> findByPath(String path);
    Folder findByPathAndMemberGroup_GroupC_GroupId(String path, Long groupId);

    List<Folder> findAllByPathAndMemberGroup_GroupC_GroupId(String path, Long groupId);
    Folder findByFolderNameAndMemberGroup(String folderName, MemberGroup memberGroup);
    Folder findByPathAndMemberGroup_GroupC_GroupName(String path, String groupName);
}
