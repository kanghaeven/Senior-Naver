package com.ssafy.seniornaver.auth.service;

import com.ssafy.seniornaver.auth.dto.Request.UpdateProfilePictureDto;
import com.ssafy.seniornaver.auth.entity.Keyword;
import com.ssafy.seniornaver.auth.entity.Member;
import com.ssafy.seniornaver.auth.repository.KeywordRepository;
import com.ssafy.seniornaver.auth.repository.MemberRepository;
import com.ssafy.seniornaver.error.code.ErrorCode;
import com.ssafy.seniornaver.error.exception.BadRequestException;
import com.ssafy.seniornaver.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ProfileServiceImpl implements ProfileService{
    private final MemberRepository memberRepository;
    private final S3Uploader s3Uploader;
    private final KeywordRepository keywordRepository;
    @Override
    @Transactional
    public UpdateProfilePictureDto updateProfilePicture(MultipartFile multipartFile, String userId) throws IOException {
        Member member = memberRepository.findByMemberId(userId).orElseThrow(
                () -> new BadRequestException(ErrorCode.NOT_EXISTS_USER_ID)
        );

        String uploadFiles = s3Uploader.uploadFiles(multipartFile, "Profile");

        member.updateProfileUrl(uploadFiles);

        memberRepository.save(member);
        /*파일 저장*/

        return new UpdateProfilePictureDto(uploadFiles);
    }

    @Override
    public String updateNickname(String memberId, String nickname) {
        Member member = memberRepository.findByMemberId(memberId).orElseThrow(
                () -> new BadRequestException(ErrorCode.NOT_EXISTS_USER_ID)
        );

        member.updateNickname(nickname);

        Member updatedMember = memberRepository.save(member);

        return updatedMember.getNickname();
    }

    @Override
    public String updateRegion(String memberId, String region) {
        Member member = memberRepository.findByMemberId(memberId).orElseThrow(
                () -> new BadRequestException(ErrorCode.NOT_EXISTS_USER_ID)
        );

        member.updateRegion(region);

        Member updatedMember = memberRepository.save(member);

        return updatedMember.getRegion();
    }

    @Override
    @Transactional
    public List<String> updateKeywords(String memberId, List<String> keywords) {

        Member member = memberRepository.findByMemberId(memberId).orElseThrow(
                () -> new BadRequestException(ErrorCode.NOT_EXISTS_USER_ID)
        );

        // 기존 키워드 삭제
        List<Keyword> existingKeywords = keywordRepository.findByMember(member);
        keywordRepository.deleteAll(existingKeywords);

        // 새 키워드 저장
        for (String keyword : keywords) {
            Keyword newKeyword = new Keyword(keyword, member);
            keywordRepository.save(newKeyword);
        }

        return keywords;
    }
}
