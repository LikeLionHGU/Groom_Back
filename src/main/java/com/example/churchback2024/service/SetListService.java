package com.example.churchback2024.service;

import com.example.churchback2024.controller.request.setlist.MusicSetListCreateRequest;
import com.example.churchback2024.controller.response.setlist.MusicSetListResponse;
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
import org.springframework.stereotype.Service;

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

//    public MusicSetListDto createSetList(MusicSetListDto musicSetListDto) {
//        Music music = musicRepository.findByMusicId(musicSetListDto.getMusicId());
//        if(music == null)
//            throw new MusicNotFoundException();
//        GroupC group = groupRepository.findByGroupId(music.getGroup().getGroupId());
//        if(group == null)
//            throw new GroupNotFoundException();
//        MusicSetList musicSetList = MusicSetList.from(musicSetListDto, music, group);
//        return MusicSetListDto.from(musicSetList);
//    }
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



}
