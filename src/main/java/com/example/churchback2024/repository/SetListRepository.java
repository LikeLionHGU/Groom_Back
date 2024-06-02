package com.example.churchback2024.repository;

import com.example.churchback2024.domain.SetList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SetListRepository extends JpaRepository<SetList, Long> {
}
