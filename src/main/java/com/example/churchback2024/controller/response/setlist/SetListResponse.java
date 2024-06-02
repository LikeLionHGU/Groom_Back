package com.example.churchback2024.controller.response.setlist;

import com.example.churchback2024.controller.response.music.MusicListResponse;
import com.example.churchback2024.dto.SetListDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetListResponse {
    private String setListName;
    private Long groupId;
    private List<MusicSetListResponse> musicList;

    public SetListResponse(SetListDto setListDto, List<MusicSetListResponse> musicList) {
        this.setListName = setListDto.getSetListName();
        this.groupId = setListDto.getGroupId();
        this.musicList = musicList;
    }
}
