package io.ssafy.soupapi.global.external.s3.application;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import io.ssafy.soupapi.global.common.code.ErrorCode;
import io.ssafy.soupapi.global.exception.BaseExceptionHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class S3FileServiceImpl implements S3FileService {

    private final AmazonS3 amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Override
    public String generateFileName(String originalFileName) {
        int pos = originalFileName.lastIndexOf(".");
        String extension = originalFileName.substring(pos + 1);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + extension;
    }

    @Override
    public String uploadFile(String defaultPath, String localFilePath) throws IOException {
        File file = new File(localFilePath);
        if (!file.exists() || !file.isFile()) {
            throw new BaseExceptionHandler(ErrorCode.FILE_NOT_EXISTS);
        }

        String fileName = defaultPath + "/" + file.getName();
        String fileUrl = "https://" + bucket + ".s3." + region + ".amazonaws.com/" + fileName;
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(Files.probeContentType(file.toPath()));
        metadata.setContentLength(file.length());

        try (FileInputStream inputStream = new FileInputStream(file)) {
            amazonS3Client.putObject(
                    new PutObjectRequest(bucket, fileName, inputStream, metadata)
                    //.withCannedAcl(CannedAccessControlList.PublicRead)
            );
        } catch (IOException e) {
            throw new BaseExceptionHandler(ErrorCode.FAILED_TO_UPLOAD_S3_FILE);
        }

        return fileUrl;
    }

    @Override
    public String uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new BaseExceptionHandler(ErrorCode.FILE_NOT_EXISTS);
        }

        String fileName = generateFileName(file.getOriginalFilename());
        String fileUrl = "https://" + bucket + ".s3." + region + ".amazonaws.com/" + fileName;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        try {
            amazonS3Client.putObject(
                    new PutObjectRequest(bucket, fileName, file.getInputStream(), metadata)
//                            .withCannedAcl(CannedAccessControlList.PublicRead)
            );
        } catch (IOException e) {
            throw new BaseExceptionHandler(ErrorCode.FAILED_TO_UPLOAD_S3_FILE);
        }

        return fileUrl;
    }

    @Override
    public List<String> uploadFileList(List<MultipartFile> fileList) {
        return fileList.stream()
                .map(this::uploadFile)
                .collect(Collectors.toList());
    }
}
