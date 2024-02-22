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
//    @Query(value = "SELECT * FROM folder WHERE SUBSTRING_INDEX(path, '-', 1) = ?1", nativeQuery = true)
    Folder findByPathAndMemberGroup_GroupC_GroupId(String path, Long groupId);
    @Query(value = "SELECT * FROM folder WHERE SUBSTRING_INDEX(path, '-', 1) = ?1", nativeQuery = true)
    List<Folder> findAllByPathAndMemberGroup_GroupC_GroupId(String path, Long groupId);
//    Folder findByPathAndFolderNameAndMemberGroup_GroupC_GroupId(String path, String folderName, Long groupId);
    @Query("SELECT f FROM Folder f WHERE f.folderName = :folderName AND f.path = :path AND f.memberGroup.groupC.groupId = :groupId")
    Folder findByFolderNameAndPathAndMemberGroup_GroupC_GroupId(@Param("folderName") String folderName, @Param("path") String path, @Param("groupId") Long groupId);
}
