package io.ssafy.soupapi.domain.project.mongodb.dto.response;

import io.ssafy.soupapi.domain.project.mongodb.entity.apidocs.ApiDoc;
import io.ssafy.soupapi.domain.project.mongodb.entity.apidocs.HttpMethodType;
import lombok.Builder;

import java.util.UUID;

@Builder
public record GetSimpleApiDoc(
        UUID id,
        String domain,
        String name,
        HttpMethodType methodType,
        String uriPath,
        String description
) {

    public static GetSimpleApiDoc of(ApiDoc apiDoc) {
        return GetSimpleApiDoc.builder()
                .id(apiDoc.getId())
                .domain(apiDoc.getDomain())
                .name(apiDoc.getName())
                .methodType(apiDoc.getHttpMethodType())
                .uriPath(apiDoc.getApiUriPath())
                .description(apiDoc.getDescription())
                .build();
    }
}
