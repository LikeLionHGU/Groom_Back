package com.example.churchback2024.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.churchback2024.controller.request.group.GroupMemberUpdateRequest;
import com.example.churchback2024.controller.response.group.GroupResponse;
import com.example.churchback2024.domain.GroupC;
import com.example.churchback2024.domain.Member;
import com.example.churchback2024.domain.MemberGroup;
import com.example.churchback2024.dto.GroupDto;
import com.example.churchback2024.dto.MemberGroupDto;
import com.example.churchback2024.exception.group.DuplicateGroupException;
import com.example.churchback2024.exception.group.GroupNotFoundException;
import com.example.churchback2024.exception.member.MemberNotFoundException;
import com.example.churchback2024.repository.GroupRepository;
import com.example.churchback2024.repository.MemberGroupRepository;
import com.example.churchback2024.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class GroupService {
    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;
    private final MemberGroupRepository memberGroupRepository;
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;
    private String groupImageUrl = null;

    private String uploadFileToS3(File uploadFile, String groupName){
        UUID uuid = UUID.randomUUID();
        String fileName = groupName + "/" + uploadFile.getName() + "_" + uuid;

        putS3(uploadFile, fileName);
        removeNewFile(uploadFile);

        return fileName;
    }

    private void removeNewFile(File targetFile){
        if(targetFile.delete()){
            log.info("파일이 삭제되었습니다.");
        }else{
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    private String putS3(File uploadFile, String fileName){
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead)
        );
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("파일 이름이 비어있거나 null입니다.");
        }

        // 파일 경로가 존재하는지 확인하고, 없다면 디렉토리 생성
        File convertFile = new File(fileName);
        File parentDir = convertFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs(); // 디렉토리 생성
        }

        // 파일 이름 중복 처리 (파일이 이미 존재하면 이름에 숫자를 붙여 새로운 파일 이름 생성)
        int counter = 1;
        String newFileName = fileName;
        while (convertFile.exists()) {
            String extension = "";
            int i = fileName.lastIndexOf('.');
            if (i > 0) {
                extension = fileName.substring(i);  // 파일 확장자 추출
                newFileName = fileName.substring(0, i); // 확장자를 제외한 파일 이름
            }
            convertFile = new File(newFileName + "_" + counter + extension);
            counter++;
        }

        // 새로운 파일 이름으로 파일 생성
        try {
            if (convertFile.createNewFile()) {
                try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                    fos.write(file.getBytes());
                    System.out.println("파일 변환 성공");
                    return Optional.of(convertFile);
                }
            } else {
                System.out.println("파일 변환 실패: 파일을 생성할 수 없습니다.");
                return Optional.empty();
            }
        } catch (IOException e) {
            System.err.println("파일 쓰기 실패: " + e.getMessage());
            throw e;  // 예외를 던져서 호출자가 처리할 수 있게 함
        }
    }

    public List<GroupDto> getGroupList() {
        List<GroupC> groups = groupRepository.findAll();
        return  groups.stream()
                .map(GroupDto::from)
                .collect(Collectors.toList());
    }
    public GroupDto createGroup(GroupDto groupDto) {
        GroupC existingGroup = groupRepository.findByGroupName(groupDto.getGroupName());
        if (existingGroup != null) {
            throw new DuplicateGroupException();
        }
        Member member = memberRepository.findByMemberId(groupDto.getMemberId());
        if (member == null) {
            throw new MemberNotFoundException();
        }

        String invitationCode;
        do {
            invitationCode = generateRandomInvitationCode();
        } while (groupRepository.findByInvitationCode(invitationCode) != null);
        String defaultImg = "지히/defaultImg.svg_898e4dfc-f740-458e-850f-42f0800ba78b";

        GroupC newGroup = GroupC.from(groupDto, invitationCode, generateImageUrl(defaultImg));
        groupRepository.save(newGroup);

        MemberGroup memberGroup = MemberGroup.from(member, newGroup, groupDto, null);
        memberGroupRepository.save(memberGroup);
        return GroupDto.from(newGroup);
    }

    public GroupDto addGroup(GroupDto groupDto) {
        Member member = memberRepository.findByMemberId(groupDto.getMemberId());
        if (member == null) {
            throw new MemberNotFoundException();
        }

        List<MemberGroup> memberGroups = memberGroupRepository.findByMember(member);
        if (memberGroups.size() >= 3) {
            throw new IllegalStateException("멤버는 최대 3개의 그룹에만 가입할 수 있습니다.");
        }

        GroupC groupC = groupRepository.findByInvitationCode(groupDto.getInvitationCode());
        if (groupC == null) {
            throw new GroupNotFoundException();
        }

        MemberGroup memberGroup = memberGroupRepository.findByMemberAndGroupC(member, groupC);
        if (memberGroup != null) {
            throw new DuplicateGroupException();
        }

        memberGroup = MemberGroup.from(member, groupC, groupDto, null);
        memberGroupRepository.save(memberGroup);

        return GroupDto.from(memberGroup);
    }
    public String generateRandomInvitationCode() {
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int length = 6;

        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    public GroupResponse updateGroup(Long groupId, GroupDto groupDto, MultipartFile multipartFile) throws IOException {
        GroupC groupC = groupRepository.findById(groupId).orElseThrow(() -> new GroupNotFoundException());
        GroupC existingGroup = groupRepository.findByGroupName(groupDto.getGroupName());

        if (existingGroup != null && !existingGroup.getGroupId().equals(groupId)) {
            throw new DuplicateGroupException();
        }
        if (multipartFile != null && !multipartFile.isEmpty()) {
            File uploadFile = convert(multipartFile)
                    .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));
            groupImageUrl = uploadFileToS3(uploadFile, groupC.getGroupName());
        }

        groupC.update(groupDto, generateImageUrl(groupImageUrl));
        groupRepository.save(groupC);
        return new GroupResponse(groupC, generateImageUrl(groupImageUrl));
    }

    public void deleteGroup(Long groupId) {
        groupRepository.deleteById(groupId);
    }

    public GroupDto getGroupInfo(Long groupId, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        GroupC group = groupRepository.findById(groupId).orElseThrow();
        MemberGroup memberGroup = memberGroupRepository.findByMemberAndGroupC(member, group);
        return GroupDto.from(memberGroup);
    }

    public GroupDto getGroupInfo(Long groupId) {
        GroupC group = groupRepository.findById(groupId).orElseThrow();
        return GroupDto.from(group);
    }
    public List<GroupDto> getGroupListByMemberId(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        if(member == null) {
            throw new MemberNotFoundException();
        }
        List<MemberGroup> memberGroups = memberGroupRepository.findByMember(member);
        return memberGroups.stream()
                .map(GroupDto::from)
                .collect(Collectors.toList());
    }

    public List<MemberGroupDto> getGroupInfoListByGroupId(Long groupId) {
        GroupC group = groupRepository.findById(groupId).orElseThrow();
        List<MemberGroup> memberGroups = memberGroupRepository.findByGroupC(group);
        return memberGroups.stream()
                .map(MemberGroupDto::from)
                .collect(Collectors.toList());
    }
    private String generateImageUrl(String storedFileName) {
        return "https://" + bucket + ".s3." + region + ".amazonaws.com/" + storedFileName;
    }

    public void deleteMemberGroup(Long groupId, Long memberId) {
        GroupC group = groupRepository.findById(groupId).orElseThrow();
        Member member = memberRepository.findById(memberId).orElseThrow();
        MemberGroup memberGroup = memberGroupRepository.findByMemberAndGroupC(member, group);
        memberGroupRepository.delete(memberGroup);
    }

    public GroupDto updateMemberGroup(Long groupId, Long memberId, GroupMemberUpdateRequest groupMemberUpdateRequest) {
        GroupC group = groupRepository.findById(groupId).orElseThrow();
        Member member = memberRepository.findById(memberId).orElseThrow();
        MemberGroup memberGroup = memberGroupRepository.findByMemberAndGroupC(member, group);
        memberGroup.update(groupMemberUpdateRequest);
        memberGroupRepository.save(memberGroup);
        return GroupDto.from(memberGroup);
    }
}
