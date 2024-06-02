package com.example.churchback2024.exception.setlist;

public class SetListNotFoundException extends RuntimeException{
    public SetListNotFoundException() {
        super("요청한 세트리스트를 찾을 수 없습니다.");
    }

    public SetListNotFoundException(String message) {
        super(message);
    }
}
