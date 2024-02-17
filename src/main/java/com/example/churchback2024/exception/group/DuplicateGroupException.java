package com.example.churchback2024.exception.group;

public class DuplicateGroupException extends RuntimeException {
    public DuplicateGroupException() {
        super("이미 존재하는 회원입니다.");
    }
}
