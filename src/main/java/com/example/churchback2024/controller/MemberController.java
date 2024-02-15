package com.example.churchback2024.controller;

import com.example.churchback2024.controller.request.MemberRequest;
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
    public void findMemberList(){
        memberService.getMemberList();
    }

    @PostMapping("/create")
    public void createMember(@RequestBody MemberRequest request){
        memberService.createMember(MemberDto.from(request));
    }
}
