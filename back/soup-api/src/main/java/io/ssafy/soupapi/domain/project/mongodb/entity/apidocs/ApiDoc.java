package io.ssafy.soupapi.domain.project.mongodb.entity.apidocs;

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
    @Field
    private UUID id;
    @Field
    private String domain;
    @Field
    private String name;
    @Field("method_name")
    private String methodName;
    @Field("method_description")
    private String description;
    @Field("http_method_type")
    private HttpMethodType methodType;
    @Field("uri_path")
    private String apiUriPath;
    @Builder.Default
    @Field("valid_path_variables")
    private List<String> validPathVariableNames = new ArrayList<>();
    @Builder.Default
    @Field("path_variables")
    private List<ApiVariable> pathVariables = new ArrayList<>();
    @Builder.Default
    @Field("query_parameters")
    private List<ApiVariable> queryParameters = new ArrayList<>();
    @Field("request_body_name")
    private String requestBodyName;
    @Field("request_body")
    private String requestBody;
    @Field("response_body_name")
    private String responseBodyName;
    @Field("response_body")
    private String responseBody;
}
