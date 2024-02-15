package com.example.churchback2024.controller;

import com.example.churchback2024.controller.request.member.MemberCreateRequest;
import com.example.churchback2024.controller.request.member.MemberUpdateRequest;
import com.example.churchback2024.controller.response.member.MemberListResponse;
import com.example.churchback2024.controller.response.member.MemberResponse;
import com.example.churchback2024.domain.Member;
import com.example.churchback2024.dto.MemberDto;
import com.example.churchback2024.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/church+/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/list")
    public ResponseEntity<MemberListResponse> findMemberList(){
        MemberListResponse memberListResponse = memberService.getMemberList();
        return ResponseEntity.ok(memberListResponse);
    }

    @PostMapping("/create")
    public void createMember(@RequestBody MemberCreateRequest request){
        memberService.createMember(MemberDto.from(request));
    }
    @PatchMapping("/{memberId}")
    public ResponseEntity<Member> update(@PathVariable Long memberId, @RequestBody MemberUpdateRequest request){
        return ResponseEntity.ok(memberService.updateMember(memberId, MemberDto.from(request)));
    }

    @DeleteMapping("/{memberId}")
    public void delete(@PathVariable Long memberId){
        memberService.deleteMember(memberId);
    }

}
