package com.example.churchback2024.controller.request.setlist;

import com.example.churchback2024.domain.Music;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@Data
public class SetListCreateRequest {
    private Long memberId;
    private Long groupId;
    private String setListName;
    private List<MusicSetListCreateRequest> musicSetList;
}
