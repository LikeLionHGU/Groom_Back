package com.example.churchback2024.controller.response.Folder;


import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

    @Getter
    @NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
    public class FolderListResponse {
        private List<FolderResponse> folders;

        public FolderListResponse(List<FolderResponse> folders) {
            this.folders = folders;
        }
    }





