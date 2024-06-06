package com.example.churchback2024.dto;

import com.example.churchback2024.controller.request.setlist.MusicSetListCreateRequest;
import com.example.churchback2024.controller.request.setlist.SetListCreateRequest;
import com.example.churchback2024.controller.response.setlist.MusicSetListResponse;
import com.example.churchback2024.domain.SetList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class SetListDto {
    private Long setListId;
    private String setListName;
    private Long groupId;
    private List<MusicSetListResponse> musicList;

    public static SetListDto from(SetList setList) {
        return SetListDto.builder()
                .setListId(setList.getSetListId())
                .setListName(setList.getSetListName())
                .groupId(setList.getGroup().getGroupId())
                .build();
    }

    public static SetListDto from(SetListCreateRequest setListCreateRequest) {
        return SetListDto.builder()
                .setListName(setListCreateRequest.getSetListName())
                .groupId(setListCreateRequest.getGroupId())
                .build();
    }
}
