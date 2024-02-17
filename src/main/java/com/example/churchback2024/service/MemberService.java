package com.example.churchback2024.service;

import com.example.churchback2024.domain.Member;
import com.example.churchback2024.dto.MemberDto;
import com.example.churchback2024.exception.DuplicateMemberException;
import com.example.churchback2024.exception.MemberNotFoundException;
import com.example.churchback2024.repository.MemberRepository;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${spring.oauth2.google.resource-uri}")
    private String googleResourceUri;
    private final MemberRepository memberRepository;

    public void login(String accessToken, MemberDto memberDto) {
        JsonNode userResourceNode = getUserResource(accessToken);

        System.out.println("userResourceNode = " + userResourceNode);

        String id = userResourceNode.get("id").asText();
        String email = userResourceNode.get("email").asText();
        String name = userResourceNode.get("name").asText();

        Member member = memberRepository.findByEmail(email);
        if (member != null) {
            throw new DuplicateMemberException();
        }
        memberRepository.save(Member.from(id, name, email, memberDto));
    }

    private JsonNode getUserResource(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<JsonNode> responseEntity = restTemplate.exchange(googleResourceUri, HttpMethod.GET, entity, JsonNode.class);
        return responseEntity.getBody();
    }

    public List<MemberDto> getMemberList() {
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .map(MemberDto::from)
                .collect(Collectors.toList());
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
