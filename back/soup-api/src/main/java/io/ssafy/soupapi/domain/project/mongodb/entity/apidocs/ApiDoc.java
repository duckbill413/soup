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
    @Field("api_method_name")
    private String methodName;
    @Field("api_method_description")
    private String description;
    @Field("api_http_method_type")
    private HttpMethodType methodType;
    @Field("api_uri_path")
    private String apiUriPath;
    @Builder.Default
    @Field("api_valid_path_variables")
    private List<String> validPathVariableNames = new ArrayList<>();
    @Builder.Default
    @Field("api_path_variables")
    private List<ApiVariable> pathVariables = new ArrayList<>();
    @Builder.Default
    @Field("api_query_parameters")
    private List<ApiVariable> queryParameters = new ArrayList<>();
    @Field("api_request_body_name")
    private String requestBodyName;
    @Field("api_request_body")
    private String requestBody;
    @Field("api_response_body_name")
    private String responseBodyName;
    @Field("api_response_body")
    private String responseBody;
}
