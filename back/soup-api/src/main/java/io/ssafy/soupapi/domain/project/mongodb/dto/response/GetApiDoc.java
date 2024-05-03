package io.ssafy.soupapi.domain.project.mongodb.dto.response;

import io.ssafy.soupapi.domain.project.mongodb.entity.apidocs.ApiDoc;
import io.ssafy.soupapi.domain.project.mongodb.entity.apidocs.ApiVariable;
import io.ssafy.soupapi.domain.project.mongodb.entity.apidocs.HttpMethodType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record GetApiDoc(
        @Schema(description = "id")
        UUID id,
        @Schema(description = "domain")
        String domain,
        @Schema(description = "name")
        String name,
        @Schema(description = "method_name")
        String methodName,
        @Schema(description = "method_description")
        String description,
        @Schema(description = "http_method_type")
        HttpMethodType methodType,
        @Schema(description = "uri_path")
        String apiUriPath,
        @Schema(description = "valid_path_variables")
        List<String> validPathVariableNames,
        @Schema(description = "path_variables")
        List<ApiVariable> pathVariables,
        @Schema(description = "query_parameters")
        List<ApiVariable> queryParameters,
        @Schema(description = "request_body_name")
        String requestBodyName,
        @Schema(description = "request_body")
        String requestBody,
        @Schema(description = "response_body_name")
        String responseBodyName,
        @Schema(description = "response_body")
        String responseBody
) {

    public static GetApiDoc of(ApiDoc apiDoc) {
        return GetApiDoc.builder()
                .id(apiDoc.getId())
                .domain(apiDoc.getDomain())
                .name(apiDoc.getName())
                .methodName(apiDoc.getMethodName())
                .description(apiDoc.getDescription())
                .methodType(apiDoc.getMethodType())
                .apiUriPath(apiDoc.getApiUriPath())
                .validPathVariableNames(apiDoc.getValidPathVariableNames())
                .pathVariables(apiDoc.getPathVariables())
                .queryParameters(apiDoc.getQueryParameters())
                .requestBody(apiDoc.getRequestBody())
                .requestBodyName(apiDoc.getRequestBodyName())
                .responseBody(apiDoc.getResponseBody())
                .responseBodyName(apiDoc.getResponseBodyName())
                .build();
    }
}
