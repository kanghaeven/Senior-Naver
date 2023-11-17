package com.ssafy.seniornaver.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class S3Uploader {
    private final AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("d33nz7652hemr5.cloudfront.net")
    private String cloudFrontUrl;

    public String uploadFiles(MultipartFile multipartFile, String dirName) throws IOException {
        String originalName = multipartFile.getOriginalFilename();
        String fileName = dirName + "/" + UUID.randomUUID() + "_" + originalName; // S3에 저장된 파일 이름
        log.info("upload");

        // 임시 파일 생성
        Path tempFile = Files.createTempFile("temp", originalName.substring(originalName.lastIndexOf(".")));
        multipartFile.transferTo(tempFile.toFile());
        String contentType = Files.probeContentType(tempFile);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType);
        metadata.setContentLength(multipartFile.getSize());

        try (InputStream inputStream = new FileInputStream(tempFile.toFile())) {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, fileName, inputStream, metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead);
            amazonS3Client.putObject(putObjectRequest);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("error: MultipartFile -> InputStream convert fail");
        } finally {
            Files.delete(tempFile); // 임시 파일 삭제
        }

        log.info("upload URL : {}", fileName);
        return "https://" + cloudFrontUrl + "/" + fileName; // S3 업로드된 주소 반환
    }

    private String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

}