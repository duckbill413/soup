package io.ssafy.soupapi.domain.project.mongodb.entity.apidocs;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class ApiDocs {
    @Builder.Default
    private List<ApiDoc> apiDocs = new ArrayList<>();
}
