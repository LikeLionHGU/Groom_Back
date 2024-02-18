package com.example.churchback2024.domain;

import com.example.churchback2024.dto.MemberDto;
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
public class Member extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false)
    private String position;
    @Column(nullable = false, unique = true)
    private String email;
    private String g_id;

    public void update(MemberDto memberDto) {
        this.nickname = memberDto.getNickname();
        this.position = memberDto.getPosition();
    }
    public static Member from(MemberDto memberDto) {
        return Member.builder()
                .email(memberDto.getEmail())
                .nickname(memberDto.getNickname())
                .position(memberDto.getPosition())
                .build();
    }
}
