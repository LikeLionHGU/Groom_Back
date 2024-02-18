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

    public void createMusic(MusicDto musicDto) {
        Music music = musicRepository.findByMusicName(musicDto.getMusicName());
        if (music != null) {
            throw new DuplicateMusicException("해당 코드의 음악이 이미 존재합니다.");
        }
        musicRepository.save(Music.from(musicDto));
    }

//    public Music getMusic(Long musicId){
//        // 파라미터로 받은 musicId를 이용해서 db에서 해당 music을 찾아서 반환할 것.
//        Music music = musicRepository.findByMusicId(musicId);
//        return music;
//    }

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
        // 1. musicId를 이용해서 해당 music이 존재하는지 찾기
        Music music = musicRepository.findByMusicId(musicId);
        // 2. 있으면 삭제 / 없으면 exception 날리기
        if(music == null){
            throw new MusicNotFoundException();
        }
        musicRepository.deleteById(musicId);
    }
}
