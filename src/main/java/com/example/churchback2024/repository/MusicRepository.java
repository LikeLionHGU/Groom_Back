package com.example.churchback2024.repository;

import com.example.churchback2024.domain.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicRepository extends JpaRepository<Music, Long> {
    Music findByCode(String code);
}
