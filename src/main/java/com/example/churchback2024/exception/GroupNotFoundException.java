package com.example.churchback2024.exception;

public class GroupNotFoundException extends RuntimeException{
    public GroupNotFoundException() {
        super("존재하지 않는 그룹입니다.");
    }
}
