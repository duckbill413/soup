package io.ssafy.soupapi.domain.springinfo.api;

import io.ssafy.soupapi.domain.springinfo.application.SpringInfoService;
import io.ssafy.soupapi.domain.springinfo.dto.GetDependency;
import io.ssafy.soupapi.domain.springinfo.dto.GetVersion;
import io.ssafy.soupapi.global.common.code.SuccessCode;
import io.ssafy.soupapi.global.common.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/spring")
@RequiredArgsConstructor
@Tag(name = "Spring Boot 정보", description = "Spring Boot 버전 및 종속성 관련 정보 조회 Controller")
public class SpringInfoController {
    private final SpringInfoService springInfoService;

    @Operation(summary = "빌드 가능한 스프링 버전 정보")
    @GetMapping("/versions")
    public ResponseEntity<BaseResponse<List<GetVersion>>> usableVersions() {
        return BaseResponse.success(
                SuccessCode.SELECT_SUCCESS,
                springInfoService.usableVersions()
        );
    }

    @Operation(summary = "빌드 가능한 spring boot dependency 정보")
    @GetMapping("/dependencies")
    public ResponseEntity<BaseResponse<List<GetDependency>>> usableDependencies() {
        return BaseResponse.success(
                SuccessCode.SELECT_SUCCESS,
                springInfoService.usableDependencies()
        );
    }
}
