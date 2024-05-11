package io.ssafy.soupapi.domain.noti.dto;

import lombok.Builder;

import java.util.Objects;

// 유저 태그로 생성되는 알림이 redis에 저장되는 방식
public record MentionNotiRedis(
    String chatMessageId,
    String mentionerId, // 태그 한 사람
    String mentioneeId, // 태그 된 사람
//    String createdAt,
    Boolean isRead
){

    @Builder
    public MentionNotiRedis {
        if (Objects.isNull(isRead)) {
            isRead = Boolean.FALSE;
        }
    }

}
