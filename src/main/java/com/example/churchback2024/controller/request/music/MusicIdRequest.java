package com.example.churchback2024.controller.request.music;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@Data
public class MusicIdRequest {
    private List<Long> musicIdList;
}
