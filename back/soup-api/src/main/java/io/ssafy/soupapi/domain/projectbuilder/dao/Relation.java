package io.ssafy.soupapi.domain.projectbuilder.dao;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
public class Relation {
    public String tableId;
    @Builder.Default
    public List<String> columnIds = new ArrayList<>();
}
