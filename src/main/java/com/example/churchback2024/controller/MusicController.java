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

//    @GetMapping("/{musicId}")
//    public ResponseEntity<Music>findById(@PathVariable Long musicId){
//        Music music = musicService.getMusic(musicId);
//        return ResponseEntity.ok(music);
//    }



    @PostMapping("/create")
    public void createMusic(@RequestBody MusicCreateRequest request) {
        // void 타입으로 정의된 memberService의 createMusic메서드를 실행하겠다.
        musicService.createMusic(MusicDto.from(request));
//        MusicDto musicDto = );
//        return ResponseEntity.ok(new MusicResponse(musicDto));
    }

    @PatchMapping("/{musicId}")
    public ResponseEntity<Music> updateMusic(@PathVariable Long musicId, @RequestBody MusicUpdateRequest request) {
        return ResponseEntity.ok(musicService.updateMusic(musicId, MusicDto.from(request)));
    }

    @DeleteMapping("/{musicId}")
    public void deleteMusic(@PathVariable Long musicId) {
        musicService.deleteMusic(musicId);
//        return ResponseEntity.noContent().build();
    }
}

