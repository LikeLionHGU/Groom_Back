package com.example.churchback2024.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.example.churchback2024.controller.request.music.MusicListRequest;
import com.example.churchback2024.controller.response.music.MusicListResponse;
import com.example.churchback2024.controller.response.music.MusicResponse;
import com.example.churchback2024.domain.GroupC;
import com.example.churchback2024.domain.Music;
import com.example.churchback2024.domain.MusicSetList;
import com.example.churchback2024.dto.MusicDto;
import com.example.churchback2024.exception.group.GroupNotFoundException;
import com.example.churchback2024.exception.music.DuplicateMusicException;
import com.example.churchback2024.exception.music.MusicNotFoundException;
import com.example.churchback2024.repository.GroupRepository;
import com.example.churchback2024.repository.MusicRepository;
import com.example.churchback2024.repository.MusicSetListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
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
    private final MusicSetListRepository musicSetListRepository;
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

    private Optional<File> convert(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("파일 이름이 비어있거나 null입니다.");
        }

        // 파일 경로가 존재하는지 확인하고, 없다면 디렉토리 생성
        File convertFile = new File(fileName);
        File parentDir = convertFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs(); // 디렉토리 생성
        }

        // 파일 이름 중복 처리 (파일이 이미 존재하면 이름에 숫자를 붙여 새로운 파일 이름 생성)
        int counter = 1;
        String newFileName = fileName;
        while (convertFile.exists()) {
            String extension = "";
            int i = fileName.lastIndexOf('.');
            if (i > 0) {
                extension = fileName.substring(i);  // 파일 확장자 추출
                newFileName = fileName.substring(0, i); // 확장자를 제외한 파일 이름
            }
            convertFile = new File(newFileName + "_" + counter + extension);
            counter++;
        }

        // 새로운 파일 이름으로 파일 생성
        try {
            if (convertFile.createNewFile()) {
                try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                    fos.write(file.getBytes());
                    System.out.println("파일 변환 성공");
                    return Optional.of(convertFile);
                }
            } else {
                System.out.println("파일 변환 실패: 파일을 생성할 수 없습니다.");
                return Optional.empty();
            }
        } catch (IOException e) {
            System.err.println("파일 쓰기 실패: " + e.getMessage());
            throw e;  // 예외를 던져서 호출자가 처리할 수 있게 함
        }
    }

    public MusicDto createMusic(MusicDto musicDto, MultipartFile multipartFile) throws IOException {
        GroupC group = groupRepository.findByGroupId(musicDto.getGroupId());
        Music existingMusic = musicRepository.findByMusicNameAndGroupGroupId(musicDto.getMusicName(), musicDto.getGroupId());
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
    public MusicListResponse getMusicList(Long groupId, MusicListRequest musicListRequest) {
        List<Music> musics = musicRepository.findByGroupGroupIdAndMusicIdIn(groupId, musicListRequest.getMusicIdList());

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
        List<MusicSetList> musicSetList = musicSetListRepository.findByMusicMusicId(musicId);
        musicSetListRepository.deleteAll(musicSetList);
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

    public static byte[] convertImageToPdf(MultipartFile image) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDImageXObject pdImage = PDImageXObject.createFromByteArray(document, image.getBytes(), image.getOriginalFilename());
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        // Get dimensions of the image and the page
        float imageWidth = pdImage.getWidth();
        float imageHeight = pdImage.getHeight();
        float pageWidth = page.getMediaBox().getWidth();
        float pageHeight = page.getMediaBox().getHeight();

        // Calculate the scale factor to fit the image within the page while maintaining the aspect ratio
        float scale = Math.min(pageWidth / imageWidth, pageHeight / imageHeight);

        // Calculate the new dimensions of the image
        float scaledWidth = imageWidth * scale;
        float scaledHeight = imageHeight * scale;

        // Calculate the position to center the image on the page
        float xPosition = (pageWidth - scaledWidth) / 2;
        float yPosition = (pageHeight - scaledHeight) / 2;

        // Draw the image with the calculated dimensions and position
        contentStream.drawImage(pdImage, xPosition, yPosition, scaledWidth, scaledHeight);
        contentStream.close();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        document.save(outputStream);
        document.close();

        return outputStream.toByteArray();
    }

    private byte[] convertToPdf(byte[] imageBytes) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDImageXObject pdImage = PDImageXObject.createFromByteArray(document, imageBytes, "image");
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        // Get dimensions of the image and the page
        float imageWidth = pdImage.getWidth();
        float imageHeight = pdImage.getHeight();
        float pageWidth = page.getMediaBox().getWidth();
        float pageHeight = page.getMediaBox().getHeight();

        // Calculate the scale factor to fit the image within the page while maintaining the aspect ratio
        float scale = Math.min(pageWidth / imageWidth, pageHeight / imageHeight);

        // Calculate the new dimensions of the image
        float scaledWidth = imageWidth * scale;
        float scaledHeight = imageHeight * scale;

        // Calculate the position to center the image on the page
        float xPosition = (pageWidth - scaledWidth) / 2;
        float yPosition = (pageHeight - scaledHeight) / 2;

        // Draw the image with the calculated dimensions and position
        contentStream.drawImage(pdImage, xPosition, yPosition, scaledWidth, scaledHeight);
        contentStream.close();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        document.save(outputStream);
        document.close();

        return outputStream.toByteArray();
    }


    public String downloadMusic(Long musicId) throws IOException {
        Music music = musicRepository.findByMusicId(musicId);
        if(music == null){
            throw new MusicNotFoundException();
        }

        String musicUrl = music.getMusicImageUrl();
        String pdfFileName = music.getMusicName() + ".pdf";

        // S3에서 music 파일 다운로드
        S3Object s3Object = amazonS3.getObject(bucket, musicUrl);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();

        // 다운로드한 파일을 pdf로 변환
        byte[] pdfBytes = convertToPdf(IOUtils.toByteArray(inputStream));

        // 변환된 PDF를 임시 파일로 저장
        File pdfFile = new File(pdfFileName);
        try (FileOutputStream fos = new FileOutputStream(pdfFile)) {
            fos.write(pdfBytes);
        }

        // S3에 pdf 파일 업로드
        String pdfUrl = uploadFileToS3(pdfFile, music.getGroup().getGroupName());

        // 임시 파일 삭제
        pdfFile.delete();

        // pdf 파일 url 반환
        System.out.println("pdfUrl : " + generateImageUrl(pdfUrl));
        return generateImageUrl(pdfUrl);
    }
}
