package com.example.churchback2024.exception.groupMember;

public class MemberGroupNotFoundException extends RuntimeException{
    public MemberGroupNotFoundException() {
        super("존재하지 않는 그룹의 회원입니다.");
    }
}