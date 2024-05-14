package io.ssafy.soupapi.domain.noti.entity;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Builder
@Document(collection = "notis")
public class MNoti {
    @Id
    @Field("noti_id")
    private ObjectId id;
    @Field("noti_message")
    private String message;
    @Field("noti_is_read")
    private boolean isRead;
    @Field("noti_receiver_id")
    private Long receiverId;
    @Field("noti_tag")
    private String tag;
}
