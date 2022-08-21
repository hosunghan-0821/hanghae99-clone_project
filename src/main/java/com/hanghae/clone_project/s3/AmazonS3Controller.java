package com.hanghae.clone_project.s3;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/api/v1")
@RequiredArgsConstructor
@RestController
public class AmazonS3Controller {
    private final AwsS3Service awsS3Service;

//     * Amazon S3에 파일 업로드
    @PostMapping("/file")
    public List<String> uploadFile(@RequestPart List<MultipartFile> multipartFile) {
        return awsS3Service.uploadFile(multipartFile);
    }


//     * Amazon S3에 업로드 된 파일을 삭제
    @DeleteMapping("/file")
    public void deleteFile(@RequestParam String fileName) {
        awsS3Service.deleteFile(fileName);
    }
}
