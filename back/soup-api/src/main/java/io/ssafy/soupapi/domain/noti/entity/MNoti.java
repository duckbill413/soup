package io.ssafy.soupapi.domain.noti.entity;

import io.ssafy.soupapi.domain.noti.dto.response.NewNotiRes;
import io.ssafy.soupapi.global.util.DateConverterUtil;
import jakarta.persistence.Id;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Document(collection = "notis")
public class MNoti {
    @Id
    private ObjectId id;

    @Field("noti_title")
    private String title;
    @Field("noti_content")
    private String content;
    @Builder.Default
    @Field("noti_is_read")
    private Boolean isRead = Boolean.FALSE;

    @Field("noti_mentioner_id")
    private String senderId;
    @Field("noti_receiver_id")
    private String receiverId;

    @Field("noti_created_at")
    private Instant createdAt;

    // 팀원 태그로 언급된 알림일 경우
    @Field("noti_mentioned_project_id")
    private String projectId;
    @Field("noti_mentioned_chat_message_id")
    private String chatMessageId;

    public NewNotiRes generateNewNotiRes(String notiPhotoUrl, String projectName) {
        return NewNotiRes.builder()
                .notiId(id.toString())
                .title(title)
                .content(content)
                .isRead(isRead)
                .notiPhotoUrl(notiPhotoUrl)
                .projectId(projectId)
                .projectName(projectName)
                .chatMessageId(chatMessageId)
                .createdTime(createdAt == null ? ZonedDateTime.now() : DateConverterUtil.instantToKstZdt(createdAt))
                .build();
    }
}
