package com.example.churchback2024.controller;

import com.example.churchback2024.controller.request.setlist.MusicSetListCreateRequest;
import com.example.churchback2024.controller.request.setlist.SetListCreateRequest;
import com.example.churchback2024.controller.response.music.MusicListResponse;
import com.example.churchback2024.controller.response.setlist.MusicSetListResponse;
import com.example.churchback2024.controller.response.setlist.SetListResponse;
import com.example.churchback2024.dto.SetListDto;
import com.example.churchback2024.service.SetListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/church+/setList")
public class SetListController {
    private final SetListService setListService;

    @PostMapping("/create")
    public ResponseEntity<SetListResponse> createMusicSetList(@RequestBody SetListCreateRequest setListCreateRequest){
        SetListDto setListDto = SetListDto.from(setListCreateRequest);
        Long setListId = setListService.createSetList(setListDto);

        List<MusicSetListResponse> musicList = createMusicDescription(setListId, setListCreateRequest.getMusicSetList());
        SetListResponse setListResponse = new SetListResponse(setListDto, musicList);
        return ResponseEntity.ok(setListResponse);
    }
    private List<MusicSetListResponse> createMusicDescription(Long setListId, List<MusicSetListCreateRequest> request) {
        List<MusicSetListResponse> musicSetListResponse = setListService.createMusicSetList(setListId, request);
        return musicSetListResponse;
    }

    @GetMapping("/list/{groupId}")
    public ResponseEntity<List<SetListResponse>> findSetList(@PathVariable Long groupId) {
        List<SetListResponse> setListResponse = setListService.getSetList(groupId);
        return ResponseEntity.ok(setListResponse);
    }
    @GetMapping("/musicList/{setListId}")
    public ResponseEntity<MusicListResponse> findSetListById(@PathVariable Long setListId) {
        MusicListResponse musicListResponse = setListService.getSetListById(setListId);
        return ResponseEntity.ok(musicListResponse);
    }
}
