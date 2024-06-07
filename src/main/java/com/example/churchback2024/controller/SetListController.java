package com.example.churchback2024.controller;

import com.example.churchback2024.controller.request.music.MusicIdRequest;
import com.example.churchback2024.controller.request.setlist.SetListImagesRequest;
import com.example.churchback2024.controller.request.setlist.MusicSetListCreateRequest;
import com.example.churchback2024.controller.request.setlist.SetListCreateRequest;
import com.example.churchback2024.controller.response.music.MusicListResponse;
import com.example.churchback2024.controller.response.setlist.MusicSetListResponse;
import com.example.churchback2024.controller.response.setlist.SetListResponse;
import com.example.churchback2024.dto.SetListDto;
import com.example.churchback2024.service.SetListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/church+/setList")
public class SetListController {
    private final SetListService setListService;

    @PostMapping("/create")
    public ResponseEntity<SetListResponse> createMusicSetList(@RequestBody SetListCreateRequest setListCreateRequest){
        SetListDto setListDto = SetListDto.from(setListCreateRequest);
        Long setListId = setListService.createSetList(setListDto);
        LocalDateTime regDate = setListService.getRegDate(setListId);

        List<MusicSetListResponse> musicList = createMusicDescription(setListId, setListCreateRequest.getMusicSetList());
        SetListResponse setListResponse = new SetListResponse(setListDto, musicList, regDate);
        return ResponseEntity.ok(setListResponse);
    }
    private List<MusicSetListResponse> createMusicDescription(Long setListId, List<MusicSetListCreateRequest> request) {
        return setListService.createMusicSetList(setListId, request);
    }
    @PostMapping("/add/{setListId}")
    public void addMusicSetList(@PathVariable Long setListId, @RequestBody List<MusicSetListCreateRequest> request) {
        setListService.addMusicSetList(setListId, request);
    }
    @PatchMapping("/update/{setListId}")
    public void updateSetList(@PathVariable Long setListId, @RequestBody SetListCreateRequest setListCreateRequest) {
        SetListDto setListDto = SetListDto.from(setListCreateRequest);
        setListService.updateSetList(setListId, setListDto);
    }
    @GetMapping("/list/{groupId}")
    public ResponseEntity<List<SetListResponse>> findSetList(@PathVariable Long groupId) {
        List<SetListResponse> setListResponse = setListService.getSetList(groupId);
        return ResponseEntity.ok(setListResponse);
    }
    @GetMapping("/musicList/{setListId}")
    public ResponseEntity<MusicListResponse> findSetListById(@PathVariable Long setListId) {
        MusicListResponse musicListResponse = setListService.getSetListById(setListId);
        return ResponseEntity.ok(musicListResponse);
    }
    @DeleteMapping("/delete/{setListId}/{musicId}")
    public void deleteSetListMusic(@PathVariable Long setListId, @PathVariable Long musicId) {
        setListService.deleteSetListMusic(setListId, musicId);
    }
    @DeleteMapping("/delete/{setListId}")
    public void deleteSetList(@PathVariable Long setListId) {
        setListService.deleteSetList(setListId);
    }
    @PostMapping("/convertToPdfs")
    public ResponseEntity<byte[]> convertImagesToPdfs(@ModelAttribute SetListImagesRequest setListImagesRequest, @RequestParam("images") List<MultipartFile> images) throws IOException {
        List<byte[]> pdfFiles = setListService.convertImagesToPdfs(images);
        String setListName = setListService.getSetListName(setListImagesRequest.getSetListId());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ZipOutputStream zos = new ZipOutputStream(baos)) {
            for (int i = 0; i < pdfFiles.size(); i++) {
                ZipEntry entry = new ZipEntry(images.get(i).getName() + (i + 1) + ".pdf");
                zos.putNextEntry(entry);
                zos.write(pdfFiles.get(i));
                zos.closeEntry();
            }
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", setListName + ".zip");

        return ResponseEntity.ok()
                .headers(headers)
                .body(baos.toByteArray());
    }

    @PostMapping("/download/{setListId}")
    public ResponseEntity<List<String>> downloadSetList(@PathVariable Long setListId, @RequestBody MusicIdRequest musicIdRequest) throws IOException {
        List<String> musicUrls = setListService.downloadSetList(setListId, musicIdRequest);
        return ResponseEntity.ok(musicUrls);
    }
}
