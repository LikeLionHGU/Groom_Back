package com.example.churchback2024.repository;

import com.example.churchback2024.domain.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicRepository extends JpaRepository<Music, Long> {
    Music findByMusicName(String musicName);
    Music findByMusicId(Long musicId);

    List<Music> findByCodeContaining(String code);
    List<Music> findByMusicNameContaining(String code);
}
