package com.example.churchback2024.repository;

import com.example.churchback2024.domain.Folder;
import com.example.churchback2024.domain.MemberGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Long> {
    Folder findByFolderId(Long folderId);
    @Query(value = "SELECT * FROM folder WHERE SUBSTRING_INDEX(path, '-', 1) = ?1", nativeQuery = true)
    List<Folder> findByPath(String path);
    Folder findByPathAndGroup_GroupId(String path, Long groupId);
    @Query(value = "SELECT * FROM folder WHERE SUBSTRING_INDEX(path, '-', 1) = ?1 AND group_id = ?2", nativeQuery = true)
    List<Folder> findAllByPathAndGroup_GroupId(String path, Long groupId);
    Folder findByFolderNameAndPathAndGroup_GroupId(String folderName, String path, Long groupId);

    Folder findByGroup_GroupIdAndFolderName(Long groupId, String folderName);
}
