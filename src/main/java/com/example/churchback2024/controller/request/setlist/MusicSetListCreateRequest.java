package com.example.churchback2024.controller.request.setlist;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@Data
public class MusicSetListCreateRequest {
    private Long musicId;
    private String Description;
}
