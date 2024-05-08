package io.ssafy.soupapi.global.external.s3.api;

import io.ssafy.soupapi.global.external.s3.application.S3FileService;
import io.ssafy.soupapi.global.common.code.SuccessCode;
import io.ssafy.soupapi.global.common.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@Tag(name = "파일", description = "AWS S3에 파일 업로드 및 다운로드")
public class S3FileController {

    private final S3FileService s3FileService;

    @Operation(summary = "파일 업로드", description = "AWS S3에 파일 업로드 후 저장된 경로 url을 반환")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseResponse<List<String>>> uploadFile(
            @Parameter(description = "multipart/form-data 형식의 파일 list. 업로드 할 수 있는 최대 용량은 100MB 입니다.")
            @RequestPart("files")
                List<MultipartFile> files
    ) {
        return BaseResponse.success(
                SuccessCode.INSERT_SUCCESS,
                s3FileService.uploadFileList(files)
        );
    }

}
