package io.ssafy.soupapi.domain.project.mongodb.dto.request;

import io.ssafy.soupapi.domain.project.mongodb.entity.apidocs.ApiDoc;
import io.ssafy.soupapi.domain.project.mongodb.entity.apidocs.ApiVariable;
import io.ssafy.soupapi.domain.project.mongodb.entity.apidocs.HttpMethodType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record UpdateApiDoc(
        @Schema(description = "id")
        UUID id,
        @NotBlank
        @Schema(description = "domain")
        String domain,
        @NotBlank
        @Schema(description = "name")
        String name,
        @NotBlank
        @Schema(description = "method_name")
        String methodName,
        @Schema(description = "method_description")
        String description,
        @NotNull
        @Schema(description = "http_method_type")
        HttpMethodType methodType,
        @NotBlank
        @Schema(description = "uri_path")
        String apiUriPath,
        @Schema(description = "path_variables")
        List<ApiVariable> pathVariables,
        @Schema(description = "query_parameters")
        List<ApiVariable> queryParameters,
        @Schema(description = "request_body")
        String requestBody,
        @Schema(description = "response_body")
        String responseBody
) {
    public static ApiDoc toApiDoc(UpdateApiDoc apiDoc) {
        return ApiDoc.builder()
                .id(apiDoc.id())
                .domain(apiDoc.domain())
                .name(apiDoc.name())
                .methodName(apiDoc.methodName())
                .description(apiDoc.description())
                .httpMethodType(apiDoc.methodType())
                .apiUriPath(apiDoc.apiUriPath())
                .pathVariables(apiDoc.pathVariables())
                .queryParameters(apiDoc.queryParameters())
                .requestBody(apiDoc.requestBody())
                .responseBody(apiDoc.responseBody())
                .build();
    }
}
