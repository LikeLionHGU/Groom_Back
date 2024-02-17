//package com.example.churchback2024.service;
//
//import com.example.churchback2024.domain.Folder;
//import com.example.churchback2024.dto.FolderDto;
//import com.example.churchback2024.exception.folder.FolderNotFoundException;
//import com.example.churchback2024.repository.FolderRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class FolderService {
//    private final FolderRepository folderRepository;
//
//    public void createFolder(FolderDto folderDto) {
//        folderRepository.save(Folder.from(folderDto));
//    }
//
//    public List<FolderDto> getFolderList() {
//        List<Folder> folders = folderRepository.findAll();
//        return folders.stream()
//                .map(FolderDto::new)
//                .collect(Collectors.toList());
//    }
//
//    public Folder updateFolder(Long folderId, FolderDto folderDto) {
//        Folder folder = folderRepository.findById(folderId)
//                .orElseThrow(() -> new FolderNotFoundException());
//        folder.update(folderDto);
//        folderRepository.save(folder);
//        return folder;
//    }
//
//    public void deleteFolder(Long folderId) {
//        folderRepository.deleteById(folderId);
//    }
//}
