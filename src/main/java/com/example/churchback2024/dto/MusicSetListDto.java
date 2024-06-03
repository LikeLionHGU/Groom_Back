package com.example.churchback2024.dto;

import com.example.churchback2024.controller.response.setlist.MusicSetListResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class MusicSetListDto {
    private Long musicId;
    private Long setListId;
    private List<MusicSetListResponse> musicDescriptionList;
}
