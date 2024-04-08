package com.example.churchback2024.domain;

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
    @Column(nullable = false, unique = true)
    private String email;
    public static Member from(String name, String email) {
        return Member.builder()
                .name(name)
                .email(email)
                .build();
    }
}
