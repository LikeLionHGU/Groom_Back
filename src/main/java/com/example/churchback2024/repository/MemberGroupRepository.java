package com.example.churchback2024.repository;

import com.example.churchback2024.domain.GroupC;
import com.example.churchback2024.domain.Member;
import com.example.churchback2024.domain.MemberGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberGroupRepository extends JpaRepository<MemberGroup, Long> {
    MemberGroup findByMember_MemberIdAndGroupC_GroupId(Long memberId, Long groupId);

    MemberGroup findByMemberAndGroupC(Member member, GroupC groupC);

    List<MemberGroup> findByMember(Member member);
}
