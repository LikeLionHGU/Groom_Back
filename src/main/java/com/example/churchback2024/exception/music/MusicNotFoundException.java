package com.example.churchback2024.exception.music;

public class MusicNotFoundException extends RuntimeException {
    public MusicNotFoundException() {
        super("요청한 음악을 찾을 수 없습니다.");
    }

    public MusicNotFoundException(String message) {
        super(message);
    }
}
