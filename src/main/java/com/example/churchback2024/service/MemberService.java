package com.example.churchback2024.service;

import com.example.churchback2024.domain.Member;
import com.example.churchback2024.dto.MemberDto;
import com.example.churchback2024.repository.MemberRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${spring.oauth2.google.resource-uri}")
    private String googleResourceUri;
    @Value("${spring.oauth2.kakao.client-id}")
    private String kakaoClientId;
    @Value("${spring.oauth2.kakao.redirect-uri}")
    private String kakaoRedirectUri;
    @Value("${spring.oauth2.kakao.resource-uri}")
    String kakaoResourceUri;
    private final MemberRepository memberRepository;

    public MemberDto login(String accessToken) {
        JsonNode userResourceNode = getUserResource(accessToken, googleResourceUri);

        String id = userResourceNode.get("id").asText();
        String email = userResourceNode.get("email").asText();
        String name = userResourceNode.get("name").asText();

        Member member = memberRepository.findByEmail(email);
        if(member == null){
            Member newMember = Member.builder()
                    .email(email)
                    .googleId(id)
                    .name(name)
                    .build();
            memberRepository.save(newMember);
            member = newMember;
        }
        return MemberDto.from(member.getName(), member.getEmail(), member.getMemberId());
    }

    public String getKakaoAccessToken (String code) {
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=").append(kakaoClientId);
            sb.append("&redirect_uri=").append(kakaoRedirectUri);
            sb.append("&code=").append(code);
            bw.write(sb.toString());
            bw.flush();

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return access_Token;
    }

    public MemberDto kakaoLogin(String accessToken) {
        JsonNode kakaoUserInfo = getUserResource(accessToken, kakaoResourceUri);
        System.out.println("kakaoUserInfo = " + kakaoUserInfo);
        String nickname = kakaoUserInfo.get("properties").get("nickname").asText();

        Member member = memberRepository.findByName(nickname);
        if(member == null){
            Member newMember = Member.builder()
                    .name(nickname)
                    .email("kakaologin")
                    .build();
            memberRepository.save(newMember);
            member = newMember;
        }
        return MemberDto.from(member.getName());
    }

    private JsonNode getUserResource(String accessToken, String resourceUri) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<JsonNode> responseEntity = restTemplate.exchange(resourceUri, HttpMethod.GET, entity, JsonNode.class);
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
}
