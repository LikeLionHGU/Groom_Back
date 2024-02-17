package com.example.churchback2024.controller;

import com.example.churchback2024.controller.request.music.MusicCreateRequest;
import com.example.churchback2024.controller.request.music.MusicUpdateRequest;
import com.example.churchback2024.controller.response.music.MusicListResponse;
import com.example.churchback2024.controller.response.music.MusicResponse;
import com.example.churchback2024.domain.Music;
import com.example.churchback2024.dto.MusicDto;
import com.example.churchback2024.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/church+/music")
public class MusicController {
    private final MusicService musicService;

    @GetMapping("/list")
    public ResponseEntity<MusicListResponse> findMusicList() {
        MusicListResponse musicListResponse = musicService.getMusicList();
        return ResponseEntity.ok(musicListResponse);
    }

    @PostMapping("/create")
    public ResponseEntity<MusicResponse> createMusic(@RequestBody MusicCreateRequest request) {
        MusicDto musicDto = musicService.createMusic(MusicDto.from(request));
        return ResponseEntity.ok(new MusicResponse(musicDto));
    }

    @PatchMapping("/{musicId}")
    public ResponseEntity<Music> updateMusic(@PathVariable Long musicId, @RequestBody MusicUpdateRequest request) {
        return ResponseEntity.ok(musicService.updateMusic(musicId, MusicDto.from(request)));
    }

    @DeleteMapping("/{musicId}")
    public ResponseEntity<Void> deleteMusic(@PathVariable Long musicId) {
        musicService.deleteMusic(musicId);
        return ResponseEntity.noContent().build();
    }
}

