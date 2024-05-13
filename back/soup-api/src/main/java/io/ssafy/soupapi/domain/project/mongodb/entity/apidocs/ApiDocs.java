package io.ssafy.soupapi.domain.project.mongodb.entity.apidocs;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class ApiDocs {
    @Field("api_base_request_body")
    private ApiBaseRequestBody baseRequestBody;
    @Field("api_base_response_body")
    private ApiBaseResponseBody baseResponseBody;
    @Field("usable_domains")
    private List<String> domains;
    @Builder.Default
    @Field("api_docs")
    private List<ApiDoc> apiDocList = new ArrayList<>();
}
