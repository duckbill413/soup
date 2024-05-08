package io.ssafy.soupapi.domain.project.mongodb.entity.apidocs;

import org.springframework.data.mongodb.core.mapping.Field;

public class ApiBaseResponseBody {
    @Field("base_response_body")
    String responseBody;
}
