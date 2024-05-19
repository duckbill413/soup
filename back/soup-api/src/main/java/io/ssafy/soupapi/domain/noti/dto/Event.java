package io.ssafy.soupapi.domain.noti.dto;

import io.ssafy.soupapi.domain.noti.constant.NotiType;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {
    NotiType notiType;
    Object data; // 주로 NewNotiRes 겠지?
    String lastEventId;
}
