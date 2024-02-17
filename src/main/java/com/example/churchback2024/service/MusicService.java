package com.example.churchback2024.service;

import com.example.churchback2024.controller.response.music.MusicListResponse;
import com.example.churchback2024.controller.response.music.MusicResponse;
import com.example.churchback2024.domain.Music;
import com.example.churchback2024.dto.MusicDto;
import com.example.churchback2024.exception.music.DuplicateMusicException;
import com.example.churchback2024.exception.music.MusicNotFoundException;
import com.example.churchback2024.repository.MusicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MusicService {
    private final MusicRepository musicRepository;

    public MusicDto createMusic(MusicDto musicDto) {
        Music music = musicRepository.findByCode(musicDto.getCode());
        if (music != null) {
            throw new DuplicateMusicException("해당 코드의 음악이 이미 존재합니다.");
        }
        musicRepository.save(Music.from(musicDto));
        return new MusicDto();
    }

    public MusicListResponse getMusicList() {
        List<Music> musics = musicRepository.findAll();
        List<MusicResponse> musicResponses = musics.stream()
                .map(MusicResponse::new)
                .collect(Collectors.toList());
        return new MusicListResponse(musicResponses);
    }

    public Music updateMusic(Long musicId, MusicDto musicDto) {
        Music music = musicRepository.findById(musicId)
                .orElseThrow(MusicNotFoundException::new);
        music.update(musicDto);
        musicRepository.save(music);
        return music;
    }

    public void deleteMusic(Long musicId) {
        musicRepository.deleteById(musicId);
    }
}
