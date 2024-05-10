package io.ssafy.soupapi.domain.readme.entity;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Builder
@Document(collection = "readme")
public class BasicTemplate {

    @Id
    @Field("_id")
    private ObjectId Id;
    @Field("title")
    private String title;
    @Field("content")
    private String content;

}