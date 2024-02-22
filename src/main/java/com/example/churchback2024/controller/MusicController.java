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

    @GetMapping("/list/{groupId}")
    public ResponseEntity<MusicListResponse> findMusicList(@PathVariable Long groupId) {
        MusicListResponse musicListResponse = musicService.getMusicList(groupId);
        return ResponseEntity.ok(musicListResponse);
    }
    @GetMapping("/list/{groupId}/{path}")
    public ResponseEntity<MusicListResponse> findMusicListByPath(@PathVariable Long groupId, @PathVariable String path) {
        MusicListResponse musicListResponse = musicService.getMusicListByPath(groupId, path);
        return ResponseEntity.ok(musicListResponse);
    }
    @GetMapping("/{musicId}")
    public ResponseEntity<MusicResponse> findMusic(@PathVariable Long musicId) {
        MusicResponse musicResponse = musicService.getMusic(musicId);
        return ResponseEntity.ok(musicResponse);
    }
    @PostMapping("/create")
    public ResponseEntity<MusicResponse> createMusic(@RequestParam(value="image", required = false) MultipartFile image, @ModelAttribute MusicCreateRequest request) throws IOException {
        MusicDto musicDto = musicService.createMusic(MusicDto.from(request), image);
        MusicResponse musicResponse = new MusicResponse(musicDto);
        return ResponseEntity.ok(musicResponse);
    }
    @PatchMapping("/{musicId}")
    public ResponseEntity<MusicResponse> updateMusic(@PathVariable Long musicId, @ModelAttribute MusicUpdateRequest request, @RequestParam(value="image", required = false) MultipartFile image) throws IOException {
        MusicDto musicDto = musicService.updateMusic(musicId, MusicDto.from(request), image);
        MusicResponse musicResponse = new MusicResponse(musicDto);
        return ResponseEntity.ok(musicResponse);
    }
    @DeleteMapping("/{musicId}")
    public void deleteMusic(@PathVariable Long musicId) {
        musicService.deleteMusic(musicId);
    }
    @GetMapping("/search/code/{groupId}/{code}")
    public ResponseEntity<MusicListResponse> searchMusicByCode(@PathVariable Long groupId, @PathVariable String code) {
        MusicListResponse musicListResponse = musicService.searchMusicByCode(groupId, code);
        return ResponseEntity.ok(musicListResponse);
    }
    @GetMapping("/search/musicName/{groupId}/{musicName}")
    public ResponseEntity<MusicListResponse> searchMusicByName(@PathVariable Long groupId, @PathVariable String musicName) {
        MusicListResponse musicListResponse = musicService.searchMusicByMusicName(groupId, musicName);
        return ResponseEntity.ok(musicListResponse);
    }
}

