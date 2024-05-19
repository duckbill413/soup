package io.ssafy.soupapi.global.external.s3.application;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface S3FileService {

    String generateFileName(String originalFileName);

    String uploadFile(String defaultPath, String localFilePath) throws IOException;

    String uploadFile(MultipartFile file);

    List<String> uploadFileList(List<MultipartFile> fileList);

}
