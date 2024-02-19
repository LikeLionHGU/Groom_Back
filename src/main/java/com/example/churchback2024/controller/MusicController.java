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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
    @GetMapping("/{musicId}")
    public ResponseEntity<MusicResponse> findMusic(@PathVariable Long musicId) {
        MusicResponse musicResponse = musicService.getMusic(musicId);
        return ResponseEntity.ok(musicResponse);
    }
    @PostMapping("/create")
    public void createMusic(@RequestParam(value="image", required = false) MultipartFile image, @ModelAttribute MusicCreateRequest request) throws IOException {
        musicService.createMusic(MusicDto.from(request), image, request.getPath());
    }

    @PatchMapping("/{musicId}")
    public ResponseEntity<Music> updateMusic(@PathVariable Long musicId, @ModelAttribute MusicUpdateRequest request, @RequestParam(value="image", required = false) MultipartFile image) throws IOException {
        return ResponseEntity.ok(musicService.updateMusic(musicId, MusicDto.from(request),image));
    }

    @DeleteMapping("/{musicId}")
    public void deleteMusic(@PathVariable Long musicId) {
        musicService.deleteMusic(musicId);
    }
}

