package com.example.churchback2024.repository;

import com.example.churchback2024.domain.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicRepository extends JpaRepository<Music, Long> {
    Music findByMusicId(Long musicId);
    Music findByMusicNameAndFolder_FolderId(String musicName, Long folderId);
    @Query("SELECT m FROM Music m " +
            "JOIN FETCH m.folder f " +
            "JOIN FETCH f.memberGroup mg " +
            "JOIN FETCH mg.groupC gc " +
            "WHERE gc.groupId = :groupId")
    List<Music> findByGroupContaining(@Param("groupId") Long groupId);

    @Query("SELECT m FROM Music m " +
            "JOIN FETCH m.folder f " +
            "JOIN FETCH f.memberGroup mg " +
            "JOIN FETCH mg.groupC gc " +
            "WHERE gc.groupId = :groupId AND m.code LIKE %:code%")
    List<Music> findByGroupAndCodeContaining(@Param("groupId") Long groupId, @Param("code") String code);

    @Query("SELECT m FROM Music m " +
            "JOIN FETCH m.folder f " +
            "JOIN FETCH f.memberGroup mg " +
            "JOIN FETCH mg.groupC gc " +
            "WHERE gc.groupId = :groupId AND m.musicName LIKE %:musicName%")
    List<Music> findByGroupAndMusicNameContaining(@Param("groupId") Long groupId, @Param("musicName") String musicName);

    @Query("SELECT m FROM Music m " +
            "JOIN FETCH m.folder f " +
            "JOIN FETCH f.memberGroup mg " +
            "JOIN FETCH mg.groupC gc " +
            "WHERE gc.groupId = :groupId And f.path LIKE %:path%")
    List<Music> findByGroupAndPathContaining(@Param("groupId") Long groupId, @Param("path") String path);
}
