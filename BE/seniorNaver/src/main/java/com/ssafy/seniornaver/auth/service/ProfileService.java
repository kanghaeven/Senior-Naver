package com.ssafy.seniornaver.auth.service;

import com.ssafy.seniornaver.auth.dto.Request.UpdateProfilePictureDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProfileService {
    UpdateProfilePictureDto updateProfilePicture(MultipartFile file, String id) throws IOException;

    String updateNickname(String memberId, String nickname);

    String updateRegion(String memberId, String region);

    List<String> updateKeywords(String memberId, List<String> keywords);
}
