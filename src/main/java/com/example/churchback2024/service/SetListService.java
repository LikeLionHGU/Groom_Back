package com.example.churchback2024.service;

import com.example.churchback2024.controller.request.setlist.MusicSetListCreateRequest;
import com.example.churchback2024.controller.response.music.MusicListResponse;
import com.example.churchback2024.controller.response.music.MusicResponse;
import com.example.churchback2024.controller.response.setlist.MusicSetListResponse;
import com.example.churchback2024.controller.response.setlist.SetListResponse;
import com.example.churchback2024.domain.GroupC;
import com.example.churchback2024.domain.Music;
import com.example.churchback2024.domain.MusicSetList;
import com.example.churchback2024.domain.SetList;
import com.example.churchback2024.dto.SetListDto;
import com.example.churchback2024.exception.group.GroupNotFoundException;
import com.example.churchback2024.exception.music.MusicNotFoundException;
import com.example.churchback2024.exception.setlist.SetListNotFoundException;
import com.example.churchback2024.repository.GroupRepository;
import com.example.churchback2024.repository.MusicRepository;
import com.example.churchback2024.repository.MusicSetListRepository;
import com.example.churchback2024.repository.SetListRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SetListService {
    private final SetListRepository setListRepository;
    private final GroupService groupService;
    private final GroupRepository groupRepository;
    private final MusicRepository musicRepository;
    private final MusicSetListRepository musicSetListRepository;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    public Long createSetList(SetListDto setListDto) {
        GroupC group = groupRepository.findByGroupId(setListDto.getGroupId());
        if(group == null)
            throw new GroupNotFoundException();
        SetList setList = SetList.from(setListDto, group);
        setListRepository.save(setList);
        return setList.getSetListId();
    }

    public List<MusicSetListResponse> createMusicSetList(Long setListId, List<MusicSetListCreateRequest> request) {
        SetList setList = setListRepository.findById(setListId).orElseThrow(SetListNotFoundException::new);
        List<MusicSetListResponse> responseList = new ArrayList<>();

        for (MusicSetListCreateRequest musicSetListCreateRequest : request) {
            Music music = musicRepository.findByMusicId(musicSetListCreateRequest.getMusicId());
            if (music == null) {
                throw new MusicNotFoundException();
            }
            MusicSetList musicSetList = MusicSetList.builder()
                    .description(musicSetListCreateRequest.getDescription())
                    .music(music)
                    .setList(setList)
                    .build();
            musicSetListRepository.save(musicSetList);

            MusicSetListResponse response = new MusicSetListResponse(musicSetList);
            responseList.add(response);
        }

        return responseList;
    }


    public List<SetListResponse> getSetList(Long groupId) {
        List<SetList> setList = setListRepository.findByGroupGroupId(groupId);
        List<SetListResponse> setListResponse = new ArrayList<>();
        for (SetList s : setList) {
            List<MusicSetList> musicSetList = musicSetListRepository.findBySetListSetListId(s.getSetListId());
            List<MusicSetListResponse> musicSetListResponse = new ArrayList<>();
            for (MusicSetList m : musicSetList) {
                musicSetListResponse.add(new MusicSetListResponse(m));
            }
            setListResponse.add(new SetListResponse(SetListDto.from(s), musicSetListResponse));
        }
        return setListResponse;
    }

    public MusicListResponse getSetListById(Long setListId) {
        SetList setList = setListRepository.findById(setListId).orElseThrow(SetListNotFoundException::new);
        List<MusicSetList> musicSetList = musicSetListRepository.findBySetListSetListId(setList.getSetListId());
        List<MusicResponse> musics = new ArrayList<>();
        for (MusicSetList m : musicSetList) {
            musics.add(new MusicResponse(m.getMusic(), generateImageUrl(m.getMusic().getMusicImageUrl()), m.getDescription()));
        }
        return new MusicListResponse(musics);
    }
    private String generateImageUrl(String storedFileName) {
        return "https://" + bucket + ".s3." + region + ".amazonaws.com/" + storedFileName;
    }

    public void deleteSetListMusic(Long setListId, Long musicId) {
        MusicSetList musicSetList = musicSetListRepository.findBySetListSetListIdAndMusicMusicId(setListId, musicId);
        if (musicSetList == null) {
            throw new SetListNotFoundException();
        }
        musicSetListRepository.delete(musicSetList);
    }

    public void deleteSetList(Long setListId) {
        SetList setList = setListRepository.findBySetListId(setListId);
        if (setList == null) {
            throw new SetListNotFoundException();
        }
        List<MusicSetList> musicSetList = musicSetListRepository.findBySetListSetListId(setListId);
        musicSetListRepository.deleteAll(musicSetList);
        setListRepository.deleteById(setListId);
    }

    public LocalDateTime getRegDate(Long setListId) {
        SetList setList = setListRepository.findBySetListId(setListId);
        return setList.getRegDate();
    }

    public void updateSetList(Long setListId, SetListDto setListDto) {
        SetList setList = setListRepository.findBySetListId(setListId);
        if (setList == null) {
            throw new SetListNotFoundException();
        }
        setList.update(setListDto);
    }

    public void addMusicSetList(Long setListId, List<MusicSetListCreateRequest> request) {
        SetList setList = setListRepository.findById(setListId).orElseThrow();
        for (MusicSetListCreateRequest musicSetListCreateRequest : request) {
            Music music = musicRepository.findByMusicId(musicSetListCreateRequest.getMusicId());
            if (music == null) {
                throw new MusicNotFoundException();
            }
            MusicSetList musicSetList = MusicSetList.builder()
                    .description(musicSetListCreateRequest.getDescription())
                    .music(music)
                    .setList(setList)
                    .build();
            musicSetListRepository.save(musicSetList);
        }
    }
    public List<byte[]> convertImagesToPdfs(List<MultipartFile> images) throws IOException {
        List<byte[]> pdfFiles = new ArrayList<>();
        for (MultipartFile image : images) {
            pdfFiles.add(MusicService.convertImageToPdf(image));
        }
        return pdfFiles;
    }

    public String getSetListName(Long setListId) {
        SetList setList = setListRepository.findBySetListId(setListId);
        return setList.getSetListName();
    }
}
