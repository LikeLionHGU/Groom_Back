package com.example.churchback2024.controller;

import com.example.churchback2024.controller.response.member.KaKaoMemberLoginResponse;
import com.example.churchback2024.controller.response.member.MemberListResponse;
import com.example.churchback2024.controller.response.member.MemberLoginResponse;
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
    public ResponseEntity<MemberLoginResponse> googleLogin(@RequestHeader("Authorization") String authorizationHeader){
        String accessToken = authorizationHeader.substring("Bearer ".length());
        MemberDto memberDto = memberService.login(accessToken);
        MemberLoginResponse memberLoginResponse = new MemberLoginResponse(memberDto);
        return ResponseEntity.ok(memberLoginResponse);
    }

    @GetMapping("/kakao/callback")
    public ResponseEntity<KaKaoMemberLoginResponse> kakaoCallback(@RequestParam String code) {
        String accessToken = memberService.getKakaoAccessToken(code);

        MemberDto memberDto = memberService.kakaoLogin(accessToken);
        KaKaoMemberLoginResponse kaKaoMemberLoginResponse = new KaKaoMemberLoginResponse(memberDto);

        return ResponseEntity.ok(kaKaoMemberLoginResponse);
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
