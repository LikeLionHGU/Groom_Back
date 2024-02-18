package com.example.churchback2024.exception.folder;

public class DuplicateFolderException extends RuntimeException {
    public DuplicateFolderException() {
        super("이미 존재하는 폴더입니다.");
    }
}