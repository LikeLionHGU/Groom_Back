package com.example.churchback2024.domain;

import com.example.churchback2024.dto.GroupDto;
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
public class GroupC extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId;
    private String groupName;
    private String invitationCode;
    private String groupImage;
    public void update(GroupDto groupDto, String groupImage) {
        this.groupName = groupDto.getGroupName();
        this.groupImage = groupImage;
    }

    public static GroupC from(GroupDto groupDto) {
        return GroupC.builder()
                .groupName(groupDto.getGroupName())
                .build();
    }

    public static GroupC from(GroupDto groupDto, String invitationCode, String defaultImg) {
        return GroupC.builder()
                .groupName(groupDto.getGroupName())
                .invitationCode(invitationCode)
                .groupId(groupDto.getGroupId())
                .groupImage(defaultImg)
                .build();
    }
}
