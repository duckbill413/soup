package io.ssafy.soupapi.domain.noti.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
public class NewNotiRes {
    private String notiId;
    private String title;
    private String content;
    private boolean isRead;
    private String notiPhotoUrl;
    private String projectId;
    private String chatMessageId;
    private ZonedDateTime createdTime;
}
