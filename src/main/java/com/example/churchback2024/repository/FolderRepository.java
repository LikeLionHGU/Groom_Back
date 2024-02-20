package com.example.churchback2024.repository;

import com.example.churchback2024.domain.Folder;
import com.example.churchback2024.domain.GroupC;
import com.example.churchback2024.domain.Member;
import com.example.churchback2024.domain.MemberGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Long> {
    Folder findByFolderName (String folderName);
    Folder findByPath (String path);

    Folder findByFolderNameAndMemberGroup(String folderName, MemberGroup memberGroup);

    Folder findByPathAndMemberGroup(String path, MemberGroup memberGroup);
    Folder findByPathAndMemberGroup_GroupC_GroupName(String path, String groupName);
}
