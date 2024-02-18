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
    private String name;
    @Column(nullable = false)
    private String position;
    @Column(nullable = false, unique = true)
    private String email;
    private String googleId;

    public void update(MemberDto memberDto) {
        this.position = memberDto.getPosition();
    }
    public static Member from(MemberDto memberDto) {
        return Member.builder()
                .position(memberDto.getPosition())
                .build();
    }

    public static Member from(String id, String name, String email, MemberDto memberDto) {
        return Member.builder()
                .googleId(id)
                .name(name)
                .email(email)
                .position(memberDto.getPosition())
                .build();
    }
}
