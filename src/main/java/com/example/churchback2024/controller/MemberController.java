package com.example.churchback2024.controller;

import com.example.churchback2024.controller.request.member.MemberCreateRequest;
import com.example.churchback2024.controller.request.member.MemberUpdateRequest;
import com.example.churchback2024.controller.response.member.MemberListResponse;

import com.example.churchback2024.domain.Member;
import com.example.churchback2024.dto.MemberDto;
import com.example.churchback2024.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/church+/member")
public class MemberController {
    private final MemberService memberService;
    @PostMapping("/login")
    public void googleLogin(@RequestHeader("Authorization") String authorizationHeader, @RequestBody MemberCreateRequest request){
        String accessToken = authorizationHeader.substring("Bearer ".length());
        memberService.login(accessToken, MemberDto.from(request));
    }
    @GetMapping("/list")
    public ResponseEntity<MemberListResponse> findMemberList(){
        List<MemberDto> dtoList = memberService.getMemberList();
        MemberListResponse memberListResponse = new MemberListResponse(dtoList);
        return ResponseEntity.ok(memberListResponse);
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
