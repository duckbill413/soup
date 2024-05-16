package io.ssafy.soupapi.domain.project.mongodb.dto.liveblock;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.ssafy.soupapi.domain.project.mongodb.entity.apidocs.ApiDoc;
import io.ssafy.soupapi.domain.project.mongodb.entity.apidocs.HttpMethodType;
import io.ssafy.soupapi.global.common.code.ErrorCode;
import io.ssafy.soupapi.global.exception.BaseExceptionHandler;
import io.ssafy.soupapi.global.util.StringParserUtil;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static io.ssafy.soupapi.global.util.StringParserUtil.isJsonNullOrEmpty;

@Log4j2
public record LiveApiDetail(
        String id,
        @NotBlank(message = "domain은 필수입니다.")
        String domain,
        String name,
        @NotBlank(message = "method_name은 필수입니다.")
        @JsonProperty("method_name")
        String methodName,
        @NotNull(message = "method_name은 필수입니다.")
        @JsonProperty("http_method")
        String httpMethod,
        @NotBlank(message = "uri는 필수입니다.")
        String uri,
        String desc,
        @JsonProperty("path_variable")
        List<PathVariable> pathVariable,
        @JsonProperty("query_param")
        List<QueryParam> queryParam,
        @JsonProperty("request_body")
        Body requestBody,
        @JsonProperty("response_body")
        Body responseBody
) {
    public static ApiDoc toApiDoc(LiveApiDetail liveApiDetail) {
        if (isValid(liveApiDetail)) {
            return ApiDoc.builder()
                    .id(UUID.fromString(liveApiDetail.id()))
                    .domain(liveApiDetail.domain())
                    .name(liveApiDetail.name())
                    .methodName(liveApiDetail.methodName())
                    .description(liveApiDetail.desc())
                    .httpMethodType(liveApiDetail.httpMethod() != null ?
                            HttpMethodType.valueOf(liveApiDetail.httpMethod()) :
                            null)
                    .apiUriPath(liveApiDetail.uri())
                    .requestBody(isJsonNullOrEmpty(liveApiDetail.requestBody().data())
                            ? null
                            : liveApiDetail.requestBody().data())
                    .responseBody(isJsonNullOrEmpty(liveApiDetail.responseBody().data())
                            ? null
                            : liveApiDetail.responseBody().data())
                    .pathVariables(liveApiDetail.pathVariable() != null
                            ? liveApiDetail.pathVariable().stream().map(PathVariable::toApiVariable).toList()
                            : List.of())
                    .queryParameters(liveApiDetail.queryParam() != null
                            ? liveApiDetail.queryParam().stream().map(QueryParam::toApiVariable).toList()
                            : List.of())
                    .build();
        }

        return null;
    }


    private static boolean isValid(LiveApiDetail liveApiDetail) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<LiveApiDetail>> violations = validator.validate(liveApiDetail);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<LiveApiDetail> violation : violations) {
                sb.append(violation.getMessage()).append(", ");
            }
            throw new BaseExceptionHandler(ErrorCode.UNABLE_TO_USE_THIS_API_DOC, "유효성 검사 실패: " + sb);
        }

        List<String> needKeys = StringParserUtil.extractBracketsContent(liveApiDetail.uri());
        Set<String> pathKey = liveApiDetail.pathVariable().stream().map(p -> p.name().toUpperCase()).collect(Collectors.toSet());
        for (String needKey : needKeys) {
            if (!pathKey.contains(needKey.toUpperCase())) {
                throw new BaseExceptionHandler(ErrorCode.UNABLE_TO_USE_THIS_API_DOC, "유효성 검사 실패: PathVariable 정보 부족 " + needKey);
            }
        }

        return true;
    }

}
