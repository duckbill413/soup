package io.ssafy.soupapi.domain.project.mongodb.entity.apidocs;

import io.ssafy.soupapi.global.util.StringParserUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class ApiDoc {
    @Field("api_doc_id")
    private UUID id;
    @Field("domain")
    private String domain;
    @Field("api_name")
    private String name;
    @Field("method_name")
    private String methodName;
    @Field("api_description")
    private String description;
    @Field("http_method_type")
    private HttpMethodType httpMethodType;
    @Field("api_uri_path")
    private String apiUriPath;
    @Builder.Default
    @Field("path_variables")
    private List<ApiVariable> pathVariables = new ArrayList<>();
    @Builder.Default
    @Field("query_parameters")
    private List<ApiVariable> queryParameters = new ArrayList<>();
    @Field("request_body")
    private String requestBody;
    @Field("response_body")
    private String responseBody;

    public String getRequestBodyName() {
        return StringParserUtil.convertToPascalCase(methodName) + "Req";
    }

    public String getResponseBodyName() {
        return StringParserUtil.convertToPascalCase(methodName) + "Res";
    }

    public String getHttpMethod() {
        return switch (httpMethodType) {
            case GET -> "@GetMapping";
            case POST -> "@PostMapping";
            case PUT -> "@PutMapping";
            case DELETE -> "@DeleteMapping";
            case PATCH -> "@PatchMapping";
        };
    }

}
