package com.example.churchback2024.repository;

import com.example.churchback2024.domain.GroupC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<GroupC, Long> {
    GroupC findByGroupName(String groupName);
}
