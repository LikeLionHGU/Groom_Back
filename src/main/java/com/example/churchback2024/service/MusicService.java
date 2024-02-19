package com.example.churchback2024.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.churchback2024.controller.response.music.MusicListResponse;
import com.example.churchback2024.controller.response.music.MusicResponse;
import com.example.churchback2024.domain.Folder;
import com.example.churchback2024.domain.Image;
import com.example.churchback2024.domain.Music;
import com.example.churchback2024.dto.MusicDto;
import com.example.churchback2024.exception.music.DuplicateMusicException;
import com.example.churchback2024.exception.music.MusicNotFoundException;
import com.example.churchback2024.repository.FolderRepository;
import com.example.churchback2024.repository.ImageRepository;
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
    private final FolderRepository folderRepository;

    private final ImageRepository imageRepository;

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private String uploadFileToS3(File uploadFile, String dirName){
        UUID uuid = UUID.randomUUID();
        String fileName = dirName + "/" + uploadFile.getName() + "_" + uuid;
        String filePath = dirName + "/" + uploadFile.getName();

        putS3(uploadFile, fileName);
        save(MusicDto.from(fileName, filePath));
        removeNewFile(uploadFile);

        return uploadFile.getName();
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

    public void save(MusicDto musicDto) {
        Image image = Image.from(musicDto);
        imageRepository.save(image);
    }
    public void createMusic(MusicDto musicDto, MultipartFile multipartFile, String dirName) throws IOException {
        Music music = musicRepository.findByMusicName(musicDto.getMusicName());
        Folder folder = folderRepository.findByPath(musicDto.getPath());

        File uploadFile = convert(multipartFile).orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));
        uploadFileToS3(uploadFile, dirName);

        if (music != null) {
            throw new DuplicateMusicException("해당 이름의 악보가 이미 존재합니다.");
        }
        musicRepository.save(Music.from(musicDto, folder));
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
        // 1. musicId를 이용해서 해당 music이 존재하는지 찾기
        Music music = musicRepository.findByMusicId(musicId);
        // 2. 있으면 삭제 / 없으면 exception 날리기
        if(music == null){
            throw new MusicNotFoundException();
        }
        musicRepository.deleteById(musicId);
    }
}
