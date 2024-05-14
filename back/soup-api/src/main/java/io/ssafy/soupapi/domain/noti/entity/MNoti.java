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

    @Field("noti_title")
    private String title;
    @Field("noti_content")
    private String content;
    @Field("noti_is_read")
    private boolean isRead;
    @Field("noti_receiver_id")
    private String receiverId;

    // 팀원 태그로 언급된 알림일 경우
    @Field("noti_mentioned_project_id")
    private String projectId;
    @Field("noti_mentioned_chat_message_id")
    private String chatMessageId;
}
