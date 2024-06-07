package com.example.churchback2024.controller.response.setlist;

import com.example.churchback2024.dto.SetListDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetListResponse {
    private Long setListId;
    private String setListName;
    private Long groupId;
    private List<MusicSetListResponse> musicList;
    private String year;
    private String month;

    public SetListResponse(SetListDto setListDto, List<MusicSetListResponse> musicList) {
        this.setListId = setListDto.getSetListId();
        this.setListName = setListDto.getSetListName();
        this.groupId = setListDto.getGroupId();
        this.musicList = musicList;
    }
    public SetListResponse(SetListDto setListDto, List<MusicSetListResponse> musicList, LocalDateTime regDate) {
        this.setListId = setListDto.getSetListId();
        this.setListName = setListDto.getSetListName();
        this.groupId = setListDto.getGroupId();
        this.musicList = musicList;
        this.year = regDate.format(DateTimeFormatter.ofPattern("yyyy"));
        this.month = regDate.format(DateTimeFormatter.ofPattern("MM"));
    }
}
