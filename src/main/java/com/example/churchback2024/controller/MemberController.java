package com.example.churchback2024.controller;

import com.example.churchback2024.controller.request.member.MemberAddRequest;
import com.example.churchback2024.controller.response.member.MemberListResponse;

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
    public void googleLogin(@RequestHeader("Authorization") String authorizationHeader){
        String accessToken = authorizationHeader.substring("Bearer ".length());
        memberService.login(accessToken);
    }
    @PostMapping("/add")
    public void add(@RequestBody MemberAddRequest request){
        memberService.addMember(MemberDto.from(request));
    }
    @GetMapping("/list")
    public ResponseEntity<MemberListResponse> findMemberList(){
        List<MemberDto> dtoList = memberService.getMemberList();
        MemberListResponse memberListResponse = new MemberListResponse(dtoList);
        return ResponseEntity.ok(memberListResponse);
    }

    @DeleteMapping("/{memberId}")
    public void delete(@PathVariable Long memberId){
        memberService.deleteMember(memberId);
    }

}
