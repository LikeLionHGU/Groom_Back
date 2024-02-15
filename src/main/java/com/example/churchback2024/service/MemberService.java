package com.example.churchback2024.service;

import com.example.churchback2024.controller.response.member.MemberListResponse;
import com.example.churchback2024.controller.response.member.MemberResponse;
import com.example.churchback2024.domain.Member;
import com.example.churchback2024.dto.MemberDto;
import com.example.churchback2024.exception.DuplicateMemberException;
import com.example.churchback2024.exception.MemberNotFoundException;
import com.example.churchback2024.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    public void createMember(MemberDto memberDto) {
        Member member = memberRepository.findByEmail(memberDto.getEmail());
        if(member != null){
            System.out.println("이미 member에 있는 사람입니당.");
            throw new DuplicateMemberException();
        }
        memberRepository.save(Member.from(memberDto));
    }
    public MemberListResponse getMemberList() {
        List<Member> members = memberRepository.findAll();
        List<MemberResponse> memberResponses = members.stream()
                .map(MemberResponse::new)
                .collect(Collectors.toList());
        return new MemberListResponse(memberResponses);
    }
    public Member updateMember(Long memberId, MemberDto memberDto) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException());
        member.update(memberDto);
        memberRepository.save(member);
        return member;
    }

    public void deleteMember(Long memberId) {
        memberRepository.deleteById(memberId);
    }
}
