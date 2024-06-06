package com.example.churchback2024.domain;

import com.mysql.cj.protocol.ColumnDefinition;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

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
    @Builder.Default
    private Boolean isNew = true;
    public static Member from(String name, String email) {
        return Member.builder()
                .name(name)
                .email(email)
                .build();
    }
    public void update(Boolean isNew) {
        this.isNew = isNew;
    }
}
