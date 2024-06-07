package com.example.churchback2024.domain;

import com.example.churchback2024.controller.request.setlist.SetListCreateRequest;
import com.example.churchback2024.dto.SetListDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SetList extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long setListId;
    private String setListName;

    @ManyToOne
    @JoinColumn(name = "groupId")
    private GroupC group;

    public static SetList from(SetListDto setListDto, GroupC group) {
        return SetList.builder()
                .setListName(setListDto.getSetListName())
                .group(group)
                .build();
    }
    public void update(SetListDto setListDto) {
        this.setListName = setListDto.getSetListName();
    }

}
