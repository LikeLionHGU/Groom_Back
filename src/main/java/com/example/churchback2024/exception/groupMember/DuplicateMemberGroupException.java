package com.example.churchback2024.exception.groupMember;

public class DuplicateMemberGroupException extends RuntimeException {
    public DuplicateMemberGroupException() {
        super("이미 존재하는 그룹원입니다.");
    }
}