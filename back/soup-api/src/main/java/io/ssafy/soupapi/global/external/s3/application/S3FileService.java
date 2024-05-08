package io.ssafy.soupapi.global.external.s3.application;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface S3FileService {

    public String generateFileName(String originalFileName);

    public String uploadFile(MultipartFile file);

    public List<String> uploadFileList(List<MultipartFile> fileList);

}
