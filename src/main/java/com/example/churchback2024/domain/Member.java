package com.example.churchback2024.domain;

import com.example.churchback2024.dto.MemberDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    private String nickname;
    private String position;
    private String email;
    private String g_id;

    public void update(MemberDto memberDto) {
        this.nickname = memberDto.getNickname();
        this.position = memberDto.getPosition();
    }

    public static Member from(MemberDto memberDto) {
        return Member.builder()
                .nickname(memberDto.getNickname())
                .position(memberDto.getPosition())
                .build();
    }
}
