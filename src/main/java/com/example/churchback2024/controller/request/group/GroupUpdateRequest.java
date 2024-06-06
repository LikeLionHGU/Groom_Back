package com.example.churchback2024.controller.request.group;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
@Data
public class GroupUpdateRequest {
    private String groupName;
    private String description;
}
