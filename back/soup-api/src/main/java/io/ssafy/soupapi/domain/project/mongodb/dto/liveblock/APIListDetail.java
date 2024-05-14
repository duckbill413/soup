package io.ssafy.soupapi.domain.project.mongodb.dto.liveblock;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record APIListDetail(
        String id,
        String domain,
        String name,
        @JsonProperty("method_name") String methodName,
        @JsonProperty("http_method") String httpMethod,
        String uri,
        String desc,
        @JsonProperty("path_variable") List<PathVariable> pathVariable,
        @JsonProperty("query_param") List<QueryParam> queryParam,
        @JsonProperty("request_body") Body requestBody,
        @JsonProperty("response_body") Body responseBody
) {
}
