package com.example.churchback2024.domain;

import com.example.churchback2024.dto.GroupDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberGroup extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberGroupId;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "groupId")
    private GroupC groupC;

    private String position;
    private String nickname;
    public static MemberGroup from(Member member, GroupC groupC, GroupDto groupDto){
        return MemberGroup.builder()
                .member(member)
                .groupC(groupC)
                .position(groupDto.getPosition())
                .nickname(groupDto.getNickname())
                .build();
    }
}
