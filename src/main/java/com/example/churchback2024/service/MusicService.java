package com.example.churchback2024.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.churchback2024.controller.response.music.MusicListResponse;
import com.example.churchback2024.controller.response.music.MusicResponse;
import com.example.churchback2024.domain.Folder;
import com.example.churchback2024.domain.GroupC;
import com.example.churchback2024.domain.Music;
import com.example.churchback2024.dto.MusicDto;
import com.example.churchback2024.exception.folder.FolderNotFoundException;
import com.example.churchback2024.exception.group.GroupNotFoundException;
import com.example.churchback2024.exception.music.DuplicateMusicException;
import com.example.churchback2024.exception.music.MusicNotFoundException;
import com.example.churchback2024.repository.FolderRepository;
import com.example.churchback2024.repository.GroupRepository;
import com.example.churchback2024.repository.MusicRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MusicService {
    private final MusicRepository musicRepository;
    private final GroupRepository groupRepository;
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;
    private String musicImageUrl = null;

    private String uploadFileToS3(File uploadFile, String groupName){
        UUID uuid = UUID.randomUUID();
        String fileName = groupName + "/" + uploadFile.getName() + "_" + uuid;

        putS3(uploadFile, fileName);
        removeNewFile(uploadFile);

        return fileName;
    }

    private void removeNewFile(File targetFile){
        if(targetFile.delete()){
            log.info("파일이 삭제되었습니다.");
        }else{
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    private String putS3(File uploadFile, String fileName){
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead)
        );
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    private Optional<File> convert(MultipartFile file) throws  IOException {
        File convertFile = new File(file.getOriginalFilename());
        if(convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }

    public MusicDto createMusic(MusicDto musicDto, MultipartFile multipartFile) throws IOException {
        GroupC group = groupRepository.findByGroupId(musicDto.getGroupId());
        Music existingMusic = musicRepository.findByMusicName(musicDto.getMusicName());
        if (existingMusic != null) {
            throw new DuplicateMusicException();
        }
        if(group == null){
            throw new GroupNotFoundException();
        }
        if (multipartFile != null && !multipartFile.isEmpty()) {
            File uploadFile = convert(multipartFile)
                    .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));
            musicImageUrl = uploadFileToS3(uploadFile, group.getGroupName());
        }
        Music music = Music.from(musicDto, group, musicImageUrl);
        musicRepository.save(music);
        return MusicDto.from(music, generateImageUrl(music.getMusicImageUrl()));
    }

    public MusicListResponse getMusicList(Long groupId) {
        List<Music> musics = musicRepository.findByGroupContaining(groupId);

        List<MusicResponse> musicResponses = musics.stream()
                .map(music -> new MusicResponse(music, generateImageUrl(music.getMusicImageUrl())))
                .collect(Collectors.toList());
        return new MusicListResponse(musicResponses);
    }

    public MusicListResponse getMusicListByPath(Long groupId) {
        List<Music> musics = musicRepository.findByGroupGroupId(groupId);

        List<MusicResponse> musicResponses = musics.stream()
                .map(music -> new MusicResponse(music, generateImageUrl(music.getMusicImageUrl())))
                .collect(Collectors.toList());
        return new MusicListResponse(musicResponses);
    }

    public MusicResponse getMusic(Long musicId) {
        Music music = musicRepository.findById(musicId).orElseThrow(MusicNotFoundException::new);
        return new MusicResponse(music, generateImageUrl(music.getMusicImageUrl()));
    }
    public MusicDto updateMusic(Long musicId, MusicDto musicDto, MultipartFile multipartFile) throws IOException {
        Music music = musicRepository.findById(musicId).orElseThrow(MusicNotFoundException::new);
        if (multipartFile != null && !multipartFile.isEmpty()) {
            File uploadFile = convert(multipartFile)
                    .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));
            musicImageUrl = uploadFileToS3(uploadFile, music.getGroup().getGroupName());
        }
        music.update(musicDto, musicImageUrl);

        musicRepository.save(music);
        return MusicDto.from(music, generateImageUrl(music.getMusicImageUrl()));
    }

    public void deleteMusic(Long musicId) {
        Music music = musicRepository.findByMusicId(musicId);
        if(music == null){
            throw new MusicNotFoundException();
        }
        musicRepository.deleteById(musicId);
    }

    public MusicListResponse searchMusicByCode(Long groupId, String code) {
        List<Music> musics = musicRepository.findByGroupAndCodeContaining(groupId, code);
        List<MusicResponse> musicResponses = musics.stream()
                .map(music -> new MusicResponse(music, generateImageUrl(music.getMusicImageUrl())))
                .collect(Collectors.toList());
        return new MusicListResponse(musicResponses);
    }
    public MusicListResponse searchMusicByMusicName(Long groupId, String musicName) {
        List<Music> musics = musicRepository.findByGroupAndMusicNameContaining(groupId, musicName);
        List<MusicResponse> musicResponses = musics.stream()
                .map(music -> new MusicResponse(music, generateImageUrl(music.getMusicImageUrl())))
                .collect(Collectors.toList());
        return new MusicListResponse(musicResponses);
    }

    private String generateImageUrl(String storedFileName) {
        return "https://" + bucket + ".s3." + region + ".amazonaws.com/" + storedFileName;
    }

}
