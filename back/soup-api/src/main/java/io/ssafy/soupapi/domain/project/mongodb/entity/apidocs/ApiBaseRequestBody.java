package io.ssafy.soupapi.domain.project.mongodb.entity.apidocs;

import org.springframework.data.mongodb.core.mapping.Field;

public class ApiBaseRequestBody {
    @Field("base_request_body")
    String requestBody;
}
