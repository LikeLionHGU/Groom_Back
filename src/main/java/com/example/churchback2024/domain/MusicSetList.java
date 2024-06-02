package com.example.churchback2024.domain;

//import com.example.churchback2024.dto.MusicSetListDto;
//import com.example.churchback2024.dto.SetListDto;
import com.example.churchback2024.controller.request.setlist.MusicSetListCreateRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MusicSetList extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long musicSetListId;
    private String description;

    @ManyToOne
    @JoinColumn(name = "musicId")
    private Music music;

    @ManyToOne
    @JoinColumn(name = "setListId")
    private SetList setList;


//    public static MusicSetList from(MusicSetListDto musicSetListDto, Music music, SetList setList) {
//        return MusicSetList.builder()
//                .description(musicSetListDto.getMusicDescriptionList())
//                .music(music)
//                .setList(setList)
//                .build();
//    }
}
