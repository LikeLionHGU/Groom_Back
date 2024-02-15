package com.example.churchback2024.service;

import com.example.churchback2024.controller.response.member.MemberListResponse;
import com.example.churchback2024.controller.response.member.MemberResponse;
import com.example.churchback2024.domain.Member;
import com.example.churchback2024.dto.MemberDto;
import com.example.churchback2024.exception.DuplicateMemberException;
import com.example.churchback2024.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    public void createMember(MemberDto from) {
        if(memberRepository.findById(from.getMemberId()).isPresent()){
            throw new DuplicateMemberException();
        }
        memberRepository.save(Member.from(from));
    }

    public MemberListResponse getMemberList() {
        List<Member> members = memberRepository.findAll();
        List<MemberResponse> memberResponses = members.stream()
                .map(MemberResponse::new)
                .collect(Collectors.toList());
        return new MemberListResponse(memberResponses);
    }
}
