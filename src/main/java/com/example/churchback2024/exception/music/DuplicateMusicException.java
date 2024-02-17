package com.example.churchback2024.exception.music;

public class DuplicateMusicException extends RuntimeException {
    public DuplicateMusicException() {
        super("이미 존재하는 음악입니다.");
    }

    public DuplicateMusicException(String message) {
        super(message);
    }
}
