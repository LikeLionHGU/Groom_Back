package com.example.churchback2024.service;

import com.example.churchback2024.domain.GroupC;
import com.example.churchback2024.domain.Member;
import com.example.churchback2024.domain.MemberGroup;
import com.example.churchback2024.dto.MemberDto;
import com.example.churchback2024.exception.group.GroupNotFoundException;
import com.example.churchback2024.exception.groupMember.DuplicateMemberGroupException;
import com.example.churchback2024.exception.member.MemberNotFoundException;
import com.example.churchback2024.repository.GroupRepository;
import com.example.churchback2024.repository.MemberGroupRepository;
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
    private final GroupRepository groupRepository;
    private final MemberGroupRepository memberGroupRepository;

    public void login(String accessToken) {
        JsonNode userResourceNode = getUserResource(accessToken);

        System.out.println("userResourceNode = " + userResourceNode);

        String id = userResourceNode.get("id").asText();
        String email = userResourceNode.get("email").asText();
        String name = userResourceNode.get("name").asText();

        memberRepository.save(Member.from(id, name, email));
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


    public void deleteMember(Long memberId) {
        memberRepository.deleteById(memberId);
    }

    public void addMember(MemberDto memberDto) {
        Member member = memberRepository.findByEmail(memberDto.getEmail());
        if (member == null) {
            throw new MemberNotFoundException();
        }
        GroupC group = groupRepository.findByGroupId(memberDto.getGroupId());
        if (group == null) {
            throw new GroupNotFoundException();
        }
        MemberGroup memberGroup = memberGroupRepository.findByMemberAndGroupC(member, group);
        if(memberGroup != null){
            throw new DuplicateMemberGroupException();
        }
        MemberGroup newMemberGroup = MemberGroup.builder()
                .member(member)
                .groupC(group)
                .position(memberDto.getPosition())
                .build();
        memberGroupRepository.save(newMemberGroup);
    }

}
