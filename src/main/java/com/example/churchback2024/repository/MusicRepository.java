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
    Music findByMusicNameAndGroupGroupId(String musicName, Long groupId);
    List<Music> findByGroupGroupId(@Param("groupId") Long groupId);
    List<Music> findByGroupGroupIdAndMusicIdIn(Long groupId, List<Long> musicId);
    @Query("SELECT m FROM Music m " +
            "WHERE m.group.groupId = :groupId")
    List<Music> findByGroupContaining(@Param("groupId") Long groupId);

    @Query("SELECT m FROM Music m " +
            "WHERE m.group.groupId = :groupId AND m.code LIKE %:code%")
    List<Music> findByGroupAndCodeContaining(@Param("groupId") Long groupId, @Param("code") String code);

    @Query("SELECT m FROM Music m " +
            "WHERE m.group.groupId = :groupId AND m.musicName LIKE %:musicName%")
    List<Music> findByGroupAndMusicNameContaining(@Param("groupId") Long groupId, @Param("musicName") String musicName);
}
