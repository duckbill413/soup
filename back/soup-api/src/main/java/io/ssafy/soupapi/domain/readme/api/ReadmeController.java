package io.ssafy.soupapi.domain.readme.api;


import io.ssafy.soupapi.domain.readme.application.ReadmeService;
import io.ssafy.soupapi.domain.readme.entity.BasicTemplate;
import io.ssafy.soupapi.global.common.code.SuccessCode;
import io.ssafy.soupapi.global.common.response.BaseResponse;
import io.ssafy.soupapi.global.security.user.UserSecurityDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Log4j2
@RestController
@RequestMapping("/api/readme")
@RequiredArgsConstructor
@Tag(name = "ReadMe", description = "ReadMe API")
public class ReadmeController {

    private final ReadmeService readmeService;

    @Operation(summary = "ReadMe 초기 템플릿 설정", description = "ReadMe 초기 템플릿을 제공합니다.")
    @GetMapping("/init/{projectId}")
    public ResponseEntity<BaseResponse<BasicTemplate>> getTemplate(
            @RequestParam(required = false, defaultValue = "Init Template") String templateName,
            @PathVariable("projectId") String projectId,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDto
    ) {
        return BaseResponse.success(
                SuccessCode.CHECK_SUCCESS,
                readmeService.getTemplate(templateName)
        );
    }
}
