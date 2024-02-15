package com.example.churchback2024.exception;

public class DuplicateMemberException extends RuntimeException {
    public DuplicateMemberException() {
        super("이미 존재하는 회원입니다.");
    }
}