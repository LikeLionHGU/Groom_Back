package com.example.churchback2024.repository;

import com.example.churchback2024.domain.MusicSetList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicSetListRepository extends JpaRepository<MusicSetList, Long>{
}
