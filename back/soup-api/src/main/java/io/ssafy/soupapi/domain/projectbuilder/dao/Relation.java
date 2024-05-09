package io.ssafy.soupapi.domain.projectbuilder.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Relation {
    public String tableId;
    @Builder.Default
    public List<String> columnIds = new ArrayList<>();
}
