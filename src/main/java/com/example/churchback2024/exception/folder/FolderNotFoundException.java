package com.example.churchback2024.exception.folder;

public class FolderNotFoundException extends RuntimeException {
    public FolderNotFoundException() {
        super("존재하지 않는 폴더입니다.");
    }
}
