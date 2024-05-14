package io.ssafy.soupapi.domain.project.mongodb.dto.liveblock;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.ssafy.soupapi.domain.project.mongodb.entity.apidocs.ApiDoc;
import io.ssafy.soupapi.domain.project.mongodb.entity.apidocs.HttpMethodType;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.UUID;

@Log4j2
public record APIListDetail(
        String id,
        String domain,
        String name,
        @JsonProperty("method_name")
        String methodName,
        @JsonProperty("http_method")
        String httpMethod,
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
    public static ApiDoc toApiDoc(APIListDetail apiListDetail) {
        return ApiDoc.builder()
                .id(UUID.fromString(apiListDetail.id()))
                .domain(apiListDetail.domain())
                .name(apiListDetail.name())
                .methodName(apiListDetail.methodName())
                .description(apiListDetail.desc())
                .httpMethodType(HttpMethodType.valueOf(apiListDetail.httpMethod()))
                .apiUriPath(apiListDetail.uri())
                .requestBody(apiListDetail.requestBody() != null
                        ? apiListDetail.requestBody().data()
                        : null)
                .responseBody(apiListDetail.responseBody() != null
                        ? apiListDetail.responseBody().data()
                        : null)
                .pathVariables(apiListDetail.pathVariable() != null
                        ? apiListDetail.pathVariable().stream().map(PathVariable::toApiVariable).toList()
                        : List.of())
                .queryParameters(apiListDetail.queryParam() != null
                        ? apiListDetail.queryParam().stream().map(QueryParam::toApiVariable).toList()
                        : List.of())
                .build();
    }
}
